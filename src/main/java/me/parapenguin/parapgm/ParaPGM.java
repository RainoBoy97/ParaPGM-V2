package me.parapenguin.parapgm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.parapenguin.parapgm.command.JoinCommand;
import me.parapenguin.parapgm.listeners.ConnectionListener;
import me.parapenguin.parapgm.map.MapLoader;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.oman.trackerdeaths.DeathPlugin;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class ParaPGM extends JavaPlugin {
	
	static ParaPGM instance;
	static List<Listener> listeners = new ArrayList<Listener>();
	
	static Match match;
	
	// sk89q's command framework
	public CommandsManager<CommandSender> commands;
	static String noPermissionsMessage = ChatColor.RED + "No permission";
	static String noConsoleMessage = "Console, you can't use these commands!";
	static String invalidCommandSyntax = ChatColor.RED + "Invalid command syntax:";

	static File mapsRepository;
	static File rotationsRepository;
	static File libsRepository;
	public List<MapLoader> maps;
	
	@Override
	public void onEnable() {
		instance = this;
		// getLog().info("Instance Loaded: " + (instance != null)); Check if the plugin is instanced.
		
		listeners.add(new ConnectionListener());
		
		for(Listener listener : listeners)
			registerListener(listener);
		
		setupCommands();
		
		maps = new ArrayList<MapLoader>();

		mapsRepository = getConfig().getString("repo.maps") != null
				? new File(getConfig().getString("repo.maps")) : new File("/home/servers/maps/");
		rotationsRepository = getConfig().getString("repo.rotations") != null
				? new File(getConfig().getString("repo.rotations")) : new File("/home/servers/rotations/");
		libsRepository = getConfig().getString("repo.libs") != null
				? new File(getConfig().getString("repo.libs")) : new File("/home/servers/libs/");

		getConfig().set("repo.maps", mapsRepository.getAbsolutePath());
		getConfig().set("repo.rotations", rotationsRepository.getAbsolutePath());
		
		File[] maps = mapsRepository.listFiles();
		for (File map : maps) {
			if (!map.isDirectory())
				continue;

			File xml = new File(map, "map.xml");
			File region = new File(map, "region");
			File level = new File(map, "level.dat");

			boolean loadable = xml.exists() && (region.exists() && region.isDirectory()) && level.exists();
			if (loadable) try { this.maps.add(MapLoader.getLoader(xml, region, level)); } catch(Exception ex) { ex.printStackTrace(); }
		}
		
		new DeathPlugin().onEnable();
	}

	public void setupCommands() {
		commands = new CommandsManager<CommandSender>() {
			@Override
			public boolean hasPermission(CommandSender sender, String permission) {
				return sender instanceof ConsoleCommandSender || sender.hasPermission(permission);
			}
		};
		
		CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);
		cmdRegister.register(JoinCommand.class);
	} 

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch (CommandPermissionsException ex) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} catch (MissingNestedCommandException ex) {
			sender.sendMessage(ChatColor.RED + ex.getUsage());
		} catch (CommandUsageException ex) {
			sender.sendMessage(ChatColor.RED + ex.getMessage());
			sender.sendMessage(ChatColor.RED + ex.getUsage());
		} catch (WrappedCommandException ex) {
			if (ex.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
				ex.printStackTrace();
			}
		} catch (CommandException ex) {
			sender.sendMessage(ChatColor.RED + ex.getMessage());
		}

		return true;
	}
	
	public static CommandsManager<CommandSender> getCommands() {
		return getInstance().commands;
	}
	
	public static String getNoPermissionMessage() {
		return noPermissionsMessage;
	}
	
	public static String getNoConsole() {
		return noConsoleMessage;
	}
	
	public static String getInvalidCommandSyntax() {
		return invalidCommandSyntax;
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
