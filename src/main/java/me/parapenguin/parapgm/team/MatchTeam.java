package me.parapenguin.parapgm.team;

import java.util.ArrayList;
import java.util.List;

import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.map.exception.InvalidTeamException;
import me.parapenguin.parapgm.player.AboutPlayer;
import me.parapenguin.parapgm.util.StringUtils;
import me.parapenguin.parapgm.util.XMLUtil;

import org.bukkit.ChatColor;
import org.dom4j.Attribute;
import org.dom4j.Element;

public class MatchTeam {
	
	String name;
	ChatColor color;
	ChatColor overhead;
	int max;
	int overfill;
	boolean observers;
	
	String display;
	boolean capped;
	boolean closed;
	
	MatchTeam(String name, ChatColor color) {
		this(name, color, color, 0, 0, true);
	}
	
	MatchTeam(String name, ChatColor color, int max) {
		this(name, color, color, max, max + max/4, false);
	}
	
	MatchTeam(String name, ChatColor color, int max, int overfill) {
		this(name, color, color, max, overfill, false);
	}
	
	MatchTeam(String name, ChatColor color, ChatColor overhead, int max, int overfill, boolean observers) {
		this.name = name;
		this.color = color;
		this.overhead = overhead;
		this.max = max;
		this.overfill = overfill;
		this.observers = observers;
		this.capped = true;
		this.closed = false;
	}
	
	public String getName() {
		return name;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public ChatColor getOverhead() {
		return overhead;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getOverfill() {
		return overfill;
	}
	
	public boolean isObservers() {
		return observers;
	}
	
	public String getDisplay() {
		return display;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}
	
	public boolean isCapped() {
		return capped;
	}
	
	public void setCapped(boolean capped) {
		this.capped = capped;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public boolean canJoin(AboutPlayer player) {
		if(isObservers()) return true;
		if(isClosed()) return false;
		
		if(size() >= getOverfill() && isCapped())
			if(player.getPlayer().hasPermission("parapgm.join.overflow")) return true;
			else return false;
		else if(size() >= getMax() && isCapped())
			if(player.getPlayer().hasPermission("parapgm.join.max")) return true;
			else return false;
		
		return true;
	}
	
	public List<AboutPlayer> getPlayers() {
		List<AboutPlayer> players = new ArrayList<AboutPlayer>();
		
		for(AboutPlayer player : AboutPlayer.getPlayers())
			if(player.getTeam() == this)
				players.add(player);
		
		return players;
	}
	
	public int size() {
		return getPlayers().size();
	}
	
	@Override
	public String toString() {
		return "MatchTeam{name=" + name + ",color=" + color.name()
				+ ",overhead=" + overhead.name() + ",max=" + max + ",overfill=" + overfill + ",size=" + size() + "}";
	}
	
	public static List<MatchTeam> parse(MapLoader loader) {
		List<MatchTeam> teams = new ArrayList<MatchTeam>();
		
		Element teamsElement = loader.getRoot().element("teams");
		for(Element team : XMLUtil.getElements(teamsElement, "team")) {
			try {
				String nameAttribute = team.getText();
				Attribute colorAttribute = team.attribute("color");
				Attribute maxAttribute = team.attribute("max");
				Attribute overflowAttribute = team.attribute("max-overfill");
				Attribute overheadAttribute = team.attribute("overhead-color");
				
				if(colorAttribute == null || maxAttribute == null || nameAttribute == null) {
					String name = "name[" + (nameAttribute != null ? nameAttribute : "null") + "]";
					String color = "color[" + (colorAttribute != null ? colorAttribute.getText() : "null") + "]";
					String max = "max[" + (maxAttribute != null ? maxAttribute.getText() : "null") + "]";

					throw new InvalidTeamException("A team was missing required arguments: " + name + ", " + color + ", " + max);
				}
				
				String name = nameAttribute;
				
				ChatColor color = StringUtils.convertStringToChatColor(colorAttribute.getText());
				if(color == null)
					throw new InvalidTeamException("A ChatColor for a team was invalid");
				
				Integer maxInteger = null;
				int max = -1;
				try { maxInteger = StringUtils.convertStringToInteger(maxAttribute.getText()); } catch(NumberFormatException e) {}
				if(maxInteger == null)
					throw new InvalidTeamException("A max players for a team was invalid");
				max = maxInteger;
				
				int overfill = -1;
				try { overfill = StringUtils.convertStringToInteger(overflowAttribute.getText()); } catch(NumberFormatException e) {}
				if(overfill < max) overfill = max + max/4;
				
				ChatColor overhead = StringUtils.convertStringToChatColor(overheadAttribute.getText());
				if(overhead == null)
					overhead = color;
				
				teams.add(new MatchTeam(name, color, overhead, max, overfill, false));
			} catch(InvalidTeamException e) {
				ParaPGM.getLog().warning("Invalid Team: " + e.getReason());
				continue;
			} catch(Exception e) {
				ParaPGM.getLog().warning("Invalid Team: " + e.getMessage());
				continue;
			}
		}
		
		if(teams.size() > 0) return teams;
		return null;
	}
	
}
