package me.parapenguin.parapgm;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.parapenguin.parapgm.listeners.ConnectionListener;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ParaPGM extends JavaPlugin {
	
	static ParaPGM instance;
	static List<Listener> listeners = new ArrayList<Listener>();
	
	static Match match;
	
	@Override
	public void onEnable() {
		instance = this;
		// getLog().info("Instance Loaded: " + (instance != null)); Check if the plugin is instanced.
		
		listeners.add(new ConnectionListener());
		
		for(Listener listener : listeners)
			registerListener(listener);
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
	
	public static boolean hasListener(Class<?> clazz) {
		return getListener(clazz) != null;
	}
	
	public static Listener getListener(Class<?> clazz) {
		for(Listener listener : listeners)
			if(listener.getClass() == clazz)
				return listener;
		
		return null;
	}
	
	public static void registerListeners(Listener... listeners) {
		for(Listener listener : listeners)
			registerListener(listener);
	}
	
	public static void registerListener(Listener listener) {
		getInstance().getServer().getPluginManager().registerEvents(listener, getInstance());
	}
	
	public static void unregisterListeners(Listener... listeners) {
		for(Listener listener : listeners)
			unregisterListener(listener);
	}
	
	public static void unregisterListener(Listener listener) {
		HandlerList.unregisterAll(listener);
	}
	
	public static void callEvents(Event... events) {
		for(Event event : events)
			getInstance().getServer().getPluginManager().callEvent(event);
	}
	
	public static void callEvent(Event event) {
		getInstance().getServer().getPluginManager().callEvent(event);
	}
	
}
