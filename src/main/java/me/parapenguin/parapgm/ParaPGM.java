package me.parapenguin.parapgm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.parapenguin.parapgm.commands.MapCommands;
import me.parapenguin.parapgm.commands.MatchCommands;
import me.parapenguin.parapgm.commands.RotationCommands;
import me.parapenguin.parapgm.listeners.ConnectionListener;
import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.rotation.Rotation;
import me.parapenguin.parapgm.rotation.RotationLoadException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.oman.trackerdeaths.DeathListener;
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
	List<MapLoader> maps;
	
	Rotation rotation;
	
	@Override
	public void onEnable() {
		instance = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		
		// getLog().info("Instance Loaded: " + (instance != null)); Check if the plugin is instanced.
		
		listeners.add(new ConnectionListener());
		
		for(Listener listener : listeners)
			registerListener(listener);
		
		setupCommands();
		
		maps = new ArrayList<MapLoader>();

		mapsRepository = getConfig().getString("repo.maps") != null
				? new File(getConfig().getString("repo.maps")) : new File("maps/");
		rotationsRepository = getConfig().getString("repo.rotations") != null
				? new File(getConfig().getString("repo.rotations")) : new File("rotation.txt");
		libsRepository = getConfig().getString("repo.libs") != null
				? new File(getConfig().getString("repo.libs")) : new File("libs/");

		getConfig().set("repo.maps", mapsRepository.getAbsolutePath());
		getConfig().set("repo.rotations", rotationsRepository.getAbsolutePath());
		getConfig().set("repo.libs", libsRepository.getAbsolutePath());
		saveConfig();
		
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
		
		if(hasPlugin("Tracker")) registerListener(new DeathListener());
		
		try {
			rotation = parseRotation(rotationsRepository);
		} catch (RotationLoadException e) {
			getLog().warning("Failed to load Rotation, attempting to create one.");
			if(rotationsRepository.exists()) {
				getLog().warning("Can't create a new Rotation because the repository already exists, consider changing this...");
				getLog().warning("Server shutting down because a Rotation could not be parsed or created.");
				getServer().shutdown();
				return;
			}
			
			try {
				rotationsRepository.createNewFile();
				FileWriter write = new FileWriter(rotationsRepository, true);
				PrintWriter print = new PrintWriter(write);
				
				for(MapLoader loader : getMaps())
					print.printf("%s" + "%n", loader.getName());
				
				print.close();
				
				rotation = parseRotation(rotationsRepository);
			} catch (IOException e1) {
				e1.printStackTrace();
				getLog().warning("Server shutting down because a Rotation could not be parsed or created.");
				getServer().shutdown();
				return;
			} catch (RotationLoadException e1) {
				getLog().warning("RotationLoadException: '" + e1.getMessage() + "'");
				getLog().warning("Server shutting down because a Rotation could not be parsed or created.");
				getServer().shutdown();
				return;
			}
		}
	}
	
	public Rotation parseRotation(File file) throws RotationLoadException {
		return Rotation.provide(file);
	}

	public void setupCommands() {
		commands = new CommandsManager<CommandSender>() {
			@Override
			public boolean hasPermission(CommandSender sender, String permission) {
				return sender instanceof ConsoleCommandSender || sender.hasPermission(permission);
			}
		};
		
		CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);
		cmdRegister.register(MatchCommands.class);
		cmdRegister.register(MapCommands.class);
		cmdRegister.register(RotationCommands.class);
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
	
	public static List<MapLoader> getMaps() {
		return getInstance().maps;
	}
	
	public static Rotation getRotation() {
		return getInstance().rotation;
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
	
	public static boolean hasPlugin(String name) {
		return getInstance().getServer().getPluginManager().getPlugin(name) != null;
	}
	
	public static Plugin getPlugin(String name) {
		if(!hasPlugin(name)) return null;
		return getInstance().getServer().getPluginManager().getPlugin(name);
	}
	
}
