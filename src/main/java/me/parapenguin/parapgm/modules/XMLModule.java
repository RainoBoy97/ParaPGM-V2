package me.parapenguin.parapgm.modules;

import me.parapenguin.parapgm.modules.BasicModule.ModuleLoadException;

import org.bukkit.event.Listener;

public interface XMLModule extends Listener {
	
	public boolean isLoaded();
	
	public void parse() throws ModuleLoadException;
	
	public boolean isUsed();
	
}
