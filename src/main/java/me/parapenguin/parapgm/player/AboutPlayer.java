package me.parapenguin.parapgm.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.event.PlayerJoinTeamEvent;
import me.parapenguin.parapgm.team.MatchTeam;

public class AboutPlayer implements Listener {
	
	static List<AboutPlayer> players = new ArrayList<AboutPlayer>();
	
	public static AboutPlayer add(Player player) {
		if(getPlayer(player) != null)
			return getPlayer(player);
		
		AboutPlayer about = new AboutPlayer(player);
		ParaPGM.registerListener(about);
		
		return about;
	}
	
	public static boolean remove(AboutPlayer player) {
		return remove(player.getPlayer());
	}
	
	public static boolean remove(Player player) {
		if(getPlayer(player) == null)
			return false;
		
		AboutPlayer about = getPlayer(player);
		players.remove(getPlayer(player));
		
		ParaPGM.unregisterListener(about);
		return true;
	}
	
	Player player;
	MatchTeam team;
	
	AboutPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public MatchTeam getTeam() {
		return team;
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerJoinTeam(PlayerJoinTeamEvent event) {
		if(event.getPlayer() != getPlayer()) return;
	}
	
	public static AboutPlayer getPlayer(Player player) {
		for(AboutPlayer about : players)
			if(about.getPlayer() == player)
				return about;
		
		return null;
	}
	
	public static List<AboutPlayer> getPlayers() {
		return players;
	}
	
}
