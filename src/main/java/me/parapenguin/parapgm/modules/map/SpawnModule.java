package me.parapenguin.parapgm.modules.map;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.modules.BasicModule;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SpawnModule extends BasicModule {
	
	public SpawnModule(MapLoader loader) {
		super(loader);
	}
	
	public static class Spawn {
		
		@Getter public static List<Spawn> spawns = new ArrayList<Spawn>();

		@Getter Spawn spawn;
		@Getter Location location;
		@Getter @Setter String label;

		public Spawn(String location) throws InvalidSpawnException {
			String[] split = location.split(",");
			double x = Double.parseDouble(split[1]);
			double y = Double.parseDouble(split[2]);
			double z = Double.parseDouble(split[3]);
			if(split.length == 4) {
				this.location = new Location(Bukkit.getServer().getWorld(split[0]), x, y, z);
			} else if(split.length == 6) {
				this.location = new Location(Bukkit.getServer().getWorld(split[0]), x, y, z, Float.parseFloat(split[4]), Float.parseFloat(split[5]));
			} else {
				throw new InvalidSpawnException("Invalid Spawn Format: " + location);
			}
			spawn = this;
			spawns.add(spawn);
		}

		public Spawn(String location, String label) throws InvalidSpawnException {
			this.label = label;
			new Spawn(location);
		}

		public Spawn(Location location) {
			this.location = location;
			spawn = this;
			spawns.add(spawn);
		}

		public Spawn(Location location, String label) {
			this.label = label;
			new Spawn(location);
		}

		public static Spawn getSpawn(Location location) {
			for(Spawn spawn : spawns) 
				if(spawn.getLocation() == location)
					return spawn;
			return null;
		}

		public static Spawn getSpawn(String label) {
			for(Spawn spawn : spawns) 
				if(spawn.getLabel().toLowerCase().contains(label.toLowerCase()))
					return spawn;
			return null;
		}

		public static List<Spawn> getSpawns(String label) {
			List<Spawn> get = new ArrayList<Spawn>();
			for(Spawn spawn : spawns) 
				if(spawn.getLabel().toLowerCase().contains(label.toLowerCase()))
					get.add(spawn);
			return get;
		}

		public class InvalidSpawnException extends Exception {

			private static final long serialVersionUID = 11938985812787L;

			@Getter String reason;

			public InvalidSpawnException(String reason) {
				this.reason = reason;
			}

		}
		
	}
	
}
