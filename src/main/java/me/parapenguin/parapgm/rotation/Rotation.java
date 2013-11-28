package me.parapenguin.parapgm.rotation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;

import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.map.MapLoader;

public class Rotation {
	
	public static Rotation provide(File rotation) throws RotationLoadException {
		List<MapLoader> loaders = new ArrayList<MapLoader>();
		
		try {
			if(!rotation.exists() || rotation.isDirectory())
				throw new RotationLoadException("The rotation must be a file and can't be a directory.");
			
			for (String rawLine : Files.readAllLines(rotation.toPath(), Charsets.UTF_8)) {
				if(MapLoader.getLoader(rawLine) == null) {
					ParaPGM.getLog().warning("Failed to find a map for '" + rawLine + "' in the rotation file");
					continue;
				}
				
				MapLoader loader = MapLoader.getLoader(rawLine);
				loaders.add(loader);
			}
		} catch(IOException e) {
			throw new RotationLoadException("Failed to read from '" + rotation.getName() + "' because of an IOException ('" + e.getMessage() + "')");
		}
		
		List<RotationSlot> slots = new ArrayList<RotationSlot>();
		for(MapLoader loader : loaders) slots.add(new RotationSlot(loader));
		
		if(slots.size() == 0)
			throw new RotationLoadException("Could not load any maps from the Rotation file!");
		
		return new Rotation(slots);
	}
	
	int current;
	List<RotationSlot> slots;
	
	Rotation(List<RotationSlot> slots) {
		this.slots = slots;
	}
	
	public List<MapLoader> getMaps() {
		List<MapLoader> loaders = new ArrayList<MapLoader>();
		
		for(RotationSlot slot : slots)
			loaders.add(slot.getLoader());
		
		return loaders;
	}
	
	public RotationSlot setNext(MapLoader loader) {
		RotationSlot rotation;
		
		if(getNext().wasSet()) {
			getNext().setLoader(loader);
			rotation = getNext();
		} else {
			rotation = new RotationSlot(loader, true);
			List<RotationSlot> start = slots.subList(0, current);
			List<RotationSlot> finish = new ArrayList<RotationSlot>();
			
			try {
				finish = slots.subList(current + 1, slots.size() - 1);
			} catch(IndexOutOfBoundsException e) {}
			
			List<RotationSlot> slots = new ArrayList<RotationSlot>();
			slots.addAll(start);
			slots.add(rotation);
			slots.addAll(finish);
			
			this.slots = slots;
		}
		
		return rotation;
	}
	
	public RotationSlot getNext() {
		try {
			return slots.get(current + 1);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public boolean hasNext() {
		return getNext() != null;
	}
	
	public RotationSlot getPrevious() {
		try {
			return slots.get(current - 1);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public boolean hasPrevious() {
		return getPrevious() != null;
	}
	
}
