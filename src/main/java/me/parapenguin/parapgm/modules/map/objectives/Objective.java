package me.parapenguin.parapgm.modules.map.objectives;

import me.parapenguin.parapgm.modules.map.TeamModule.MapTeam;

public interface Objective {
	
	public MapTeam getTeam();
	
	public boolean isComplete();
	
}
