package me.parapenguin.parapgm.modules.server;

import lombok.Getter;
import lombok.Setter;
import me.parapenguin.parapgm.modules.AdvancedModule;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public abstract class SchedulerModule implements AdvancedModule, Runnable {

	@Getter JavaPlugin instance;
	
	@Setter private boolean running;
	@Getter private BukkitTask task;
	
	public SchedulerModule(JavaPlugin plugin) {
		this.instance = plugin;
	}
	
	public boolean isRunning() {
		return task != null && running;
	}
	
	public void start(boolean async, long delay, long interval) {
		this.task = async ? instance.getServer().getScheduler().runTaskTimerAsynchronously(instance, this, delay, interval)
					: instance.getServer().getScheduler().runTaskTimer(instance, this, delay, interval);
		
		setRunning(true);
	}
	
	public void start(boolean async, long delay) {
		this.task = async ? instance.getServer().getScheduler().runTaskLaterAsynchronously(instance, this, delay)
					: instance.getServer().getScheduler().runTaskLater(instance, this, delay);
		
		setRunning(true);
	}
	
	public void start(boolean async) {
		this.task = async ? instance.getServer().getScheduler().runTaskAsynchronously(instance, this)
					: instance.getServer().getScheduler().runTask(instance, this);
		
		setRunning(true);
	}
	
	public boolean stop() {
		if(!isRunning()) return false;
		task.cancel();
		return true;
	}
	
}
