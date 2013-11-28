package me.parapenguin.parapgm.rotation;

import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.server.Match;

public class RotationSlot {
	
	boolean wasSet;
	MapLoader loader;
	
	Match match;
	
	public RotationSlot(MapLoader loader) {
		this(loader, false);
	}
	
	public RotationSlot(MapLoader loader, boolean wasSet) {
		this.wasSet = wasSet;
		this.loader = loader;
	}
	
	public boolean wasSet() {
		return wasSet;
	}
	
	public MapLoader getLoader() {
		return loader;
	}
	
	public void setLoader(MapLoader loader) {
		this.loader = loader;
		this.wasSet = true;
	}
	
	public Match setMatch() {
		return null;
	}
	
}
