package me.parapenguin.parapgm.team;

import java.util.ArrayList;
import java.util.List;

import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.util.XMLUtil;

import org.bukkit.ChatColor;
import org.dom4j.Element;

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
		
		Element teamsElement = loader.getRoot().element("teams");
		for(Element team : XMLUtil.getElements(teamsElement, "team")) {
			
		}
		
		return null;
	}
	
}
