package me.parapenguin.parapgm;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class ParaPGM extends JavaPlugin {
	
	public static ParaPGM instance;
	
	@Override
	public void onEnable() {
		instance = this;
		// getLog().info("Instance Loaded: " + (instance != null)); Check if the plugin is instanced.
	}
	
	public static ParaPGM getInstance() {
		return instance;
	}
	
	public static Logger getLog() {
		return getInstance().getLogger();
	}
	
}
