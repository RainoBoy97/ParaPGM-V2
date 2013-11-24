package me.parapenguin.parapgm.event;

import me.parapenguin.parapgm.player.AboutPlayer;
import me.parapenguin.parapgm.team.MatchTeam;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinTeamEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	AboutPlayer player;
	MatchTeam team;
	boolean cancelled = false;
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public PlayerJoinTeamEvent(AboutPlayer player, MatchTeam team) {
		this.player = player;
		this.team = team;
	}
	
	public PlayerJoinTeamEvent(Player player, MatchTeam team) throws NullArgumentException {
		this.player = AboutPlayer.getPlayer(player);
		this.team = team;
		
		if(this.player == null)
			throw new NullArgumentException("player");
	}
	
	public AboutPlayer getPlayer() {
		return player;
	}
	
	public MatchTeam getTeam() {
		return team;
	}
	
}
