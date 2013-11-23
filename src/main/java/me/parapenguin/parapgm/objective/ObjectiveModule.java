package me.parapenguin.parapgm.objective;

import org.bukkit.event.Listener;

import me.parapenguin.parapgm.team.MatchTeam;

public interface ObjectiveModule extends Listener {
	
	public boolean isComplete();
	
	public MatchTeam getTeam();
	
}
