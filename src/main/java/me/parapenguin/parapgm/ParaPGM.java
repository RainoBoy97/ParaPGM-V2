package me.parapenguin.parapgm;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ParaPGM extends JavaPlugin {
	
	public static ParaPGM instance;
	
	public static Match match;
	
	@Override
	public void onEnable() {
		instance = this;
		// getLog().info("Instance Loaded: " + (instance != null)); Check if the plugin is instanced.
	}
	
	public static ParaPGM getInstance() {
		return instance;
	}
	
	public static Match getMatch() {
		return match;
	}
	
	public static Logger getLog() {
		return getInstance().getLogger();
	}
	
	public static void registerListener(Listener listener) {
		getInstance().getServer().getPluginManager().registerEvents(listener, getInstance());
	}
	
	public static void unregisterListener(Listener listener) {
		HandlerList.unregisterAll(listener);
	}
	
	public static void callEvent(Event event) {
		getInstance().getServer().getPluginManager().callEvent(event);
	}
	
}
