package me.parapenguin.parapgm.player.rank;

import java.util.ArrayList;
import java.util.List;

import me.parapenguin.parapgm.util.Chars;

import org.bukkit.ChatColor;

public class Rank {
	
	static List<Rank> ranks = new ArrayList<Rank>();
	
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
	
	public Rank(ChatColor pre_color, Chars character, String name, int priority) {
		this(pre_color, character + "", name, priority);
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPriority() {
		return priorty;
	}
	
	public boolean add() {
		if(ranks.contains(this))
			return false;
		
		ranks.add(this);
		return true;
	}
	
	public boolean remove() {
		if(!ranks.contains(this))
			return false;
		
		ranks.remove(this);
		return true;
	}
	
	public static List<Rank> getRanks(int priority) {
		List<Rank> ranks = new ArrayList<Rank>();
		
		for(Rank rank : Rank.ranks)
			if(rank.getPriority() >= priority)
				ranks.add(rank);
		
		return ranks;
	}
	
	public static Rank getRank(String name) {
		for(Rank rank : ranks)
			if(rank.getName().equalsIgnoreCase(name))
				return rank;
		
		for(Rank rank : ranks)
			if(rank.getName().toLowerCase().contains(name.toLowerCase()))
				return rank;
		
		return null;
	}
	
}
