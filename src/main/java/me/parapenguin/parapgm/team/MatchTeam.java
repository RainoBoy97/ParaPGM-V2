package me.parapenguin.parapgm.team;

import java.util.ArrayList;
import java.util.List;

import me.parapenguin.parapgm.map.MapLoader;

import org.bukkit.ChatColor;

public class MatchTeam {
	
	String name;
	ChatColor color;
	ChatColor overhead;
	
	String display;
	
	MatchTeam(String name, ChatColor color) {
		this(name, color, color);
	}
	
	MatchTeam(String name, ChatColor color, ChatColor overhead) {
		this.name = name;
		this.color = color;
		this.overhead = overhead;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}
	
	public static List<MatchTeam> parse(MapLoader loader) {
		List<MatchTeam> teams = new ArrayList<MatchTeam>();
		
		
		
		return null;
	}
	
}
