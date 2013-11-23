package me.parapenguin.parapgm.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.parapenguin.parapgm.team.MatchTeam;

public class AboutPlayer {
	
	static List<AboutPlayer> players = new ArrayList<AboutPlayer>();
	
	Player player;
	MatchTeam team;
	
	AboutPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static AboutPlayer getPlayer(Player player) {
		for(AboutPlayer about : players)
			if(about.getPlayer() == player)
				return about;
		
		return null;
	}
	
}
