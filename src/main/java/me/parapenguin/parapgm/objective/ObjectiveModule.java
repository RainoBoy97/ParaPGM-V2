package me.parapenguin.parapgm.objective;

import org.bukkit.event.Listener;

import me.parapenguin.parapgm.module.Module;
import me.parapenguin.parapgm.player.AboutPlayer;
import me.parapenguin.parapgm.team.MatchTeam;

public interface ObjectiveModule extends Listener, Module {
	
	public void complete(AboutPlayer player);
	
	public boolean isComplete();
	
	public MatchTeam getTeam();
	
}
