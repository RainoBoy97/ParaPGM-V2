package me.parapenguin.parapgm.objective.wool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import me.parapenguin.parapgm.objective.ObjectiveModule;
import me.parapenguin.parapgm.player.AboutPlayer;
import me.parapenguin.parapgm.team.MatchTeam;

public class WoolObjective implements ObjectiveModule {
	
	MatchTeam team;
	DyeColor dye;
	ChatColor chat;
	
	boolean complete;
	
	WoolObjective(MatchTeam team, DyeColor dye, ChatColor chat) {
		this.team = team;
		this.dye = dye;
		this.chat = chat;
		
		this.complete = false;
	}

	public void complete(AboutPlayer player) {
		
	}

	public boolean isComplete() {
		return complete;
	}

	public MatchTeam getTeam() {
		return team;
	}
	
	public static List<WoolObjective> parse() {
		List<WoolObjective> wools = new ArrayList<WoolObjective>();
		
		
		
		return null;
	}
	
}
