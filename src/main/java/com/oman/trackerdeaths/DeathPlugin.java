package com.oman.trackerdeaths;

import org.bukkit.plugin.java.JavaPlugin;

public class DeathPlugin extends JavaPlugin {

    public void onDisable() {}
    
    public void onEnable() {
        if(getServer().getPluginManager().getPlugin("Tracker") == null) {
            getLogger().severe("Could not locate the Tracker plugin. Disabling.");
            getServer().getPluginManager().disablePlugin(this);
        }
        
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }
    
}
