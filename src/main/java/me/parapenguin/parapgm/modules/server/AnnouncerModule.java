package me.parapenguin.parapgm.modules.server;

import lombok.Getter;
import lombok.Setter;
import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.modules.BasicModule;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AnnouncerModule extends BasicModule {

	@Getter @Setter boolean perPlayer, started;
	@Getter @Setter long delay;
	@Getter @Setter HashMap<AnnouncerType, String> announcer = new HashMap<AnnouncerType, String>();
	@Getter @Setter OrderType order = OrderType.IN_ORDER;
	@Getter int schedulerId, prevIndex;

	public enum AnnouncerType {
		PLAYER_SPECIFIC,
		ALL,
		//TEAM,
		CONSOLE,
		EXCLUDE_CONSOLE,
	}

	public enum OrderType {
		RANDOM,
		IN_ORDER,
	}

	public AnnouncerModule(MapLoader loader) {
		super(loader);
	}

	public void start() {
		if (!started) {
			started = true;
		} else {
			instance.getServer().getScheduler().cancelTask(schedulerId);
		}
		schedulerId = startScheduler();
	}

	public void stop() {
		started = false;
	}

	private int startScheduler() {
		return instance.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
			public void run() {
				int index = (getOrder().equals(OrderType.IN_ORDER) ? (prevIndex + 1 > announcer.size() ? 0 : prevIndex + 1) : (getOrder().equals(OrderType.RANDOM) ? (announcer.size() >= 1 ? (new Random()).nextInt(announcer.size() - 1) : 0) : 0));

				String message = ChatColor.translateAlternateColorCodes('&', new ArrayList<String>(announcer.values()).get(index));
				AnnouncerType type = new ArrayList<AnnouncerType>(announcer.keySet()).get(index);

				if (type.equals(AnnouncerType.ALL)) {
					instance.getServer().broadcastMessage(message);
				} else if (type.equals(AnnouncerType.PLAYER_SPECIFIC)) {
					for (Player player : instance.getServer().getOnlinePlayers())
						player.sendMessage(message.contains("{player}") ? message.replaceAll("{player}", player.getName()) : message);
				} else if (type.equals(AnnouncerType.CONSOLE)) {
					instance.getLogger().info(message);
				} else if (type.equals(AnnouncerType.EXCLUDE_CONSOLE)) {
					for (Player player : instance.getServer().getOnlinePlayers())
						player.sendMessage(message);
				}

				prevIndex = index;
			}
		}, delay, delay);
	}

	public void add(String message) {
		add(AnnouncerType.ALL, message);
	}

	public void add(AnnouncerType type, String message) {
		announcer.put(type, message);
	}

	public boolean contains(String message) {
		return announcer.containsValue(message);
	}

	public boolean contains(AnnouncerType type) {
		return announcer.containsKey(type);
	}

}
