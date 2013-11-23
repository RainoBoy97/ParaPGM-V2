package me.parapenguin.parapgm.map;

import java.util.List;

import lombok.Getter;
import me.parapenguin.parapgm.modules.XMLModule;
import me.parapenguin.parapgm.modules.map.InfoModule;
import me.parapenguin.parapgm.modules.map.TeamModule;


public class Map {
	
	@Getter InfoModule info;
	@Getter List<XMLModule> modules;
	
	public Map(InfoModule info, List<XMLModule> modules) {
		this.info = info;
		this.modules = modules;
	}
	
	public XMLModule getModule(Class<?> type) {
		if(!XMLModule.class.isAssignableFrom(type))
			return null;
		
		for(XMLModule module : modules)
			if(module.getClass() == type)
				return module;
		
		return null;
	}
	
	public TeamModule getTeams() {
		return (TeamModule) getModule(TeamModule.class);
	}
	
}
