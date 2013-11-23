package me.parapenguin.parapgm.modules.map;

import lombok.Getter;
import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.modules.BasicModule;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

public class TeamModule extends BasicModule {

	public TeamModule(MapLoader loader) {
		super(loader);
	}
	
	public class MapTeam {
		
		@Getter String name;
		@Getter String display;
		
		@Getter ChatColor color;
		
		@Getter Team team;
		
		public MapTeam(String name, ChatColor color) {
			this.name = name;
			this.color = color;
			this.display = name;
		}
		
		public void setDisplay(String display) {
			this.display = display;
			// add code to set the users objectives (Scoreboard), etc.
		}
		
	}

}
