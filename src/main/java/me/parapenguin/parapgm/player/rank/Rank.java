package me.parapenguin.parapgm.player.rank;

import org.bukkit.ChatColor;

public class Rank {
	
	String prefix;
	String name;
	int priorty;
	
	public Rank(String prefix, String name, int priority) {
		this.prefix = prefix;
		this.name = name;
		this.priorty = priority;
	}
	
	public Rank(ChatColor pre_color, String prefix, String name, int priority) {
		this(pre_color + prefix, name, priority);
	}
	
}
