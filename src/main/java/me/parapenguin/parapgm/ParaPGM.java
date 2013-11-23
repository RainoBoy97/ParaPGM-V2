package me.parapenguin.parapgm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import lombok.Getter;
import lombok.Setter;
import me.parapenguin.parapgm.commands.CommandFrameworkCommand;
import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.modules.map.InfoModule;
import me.parapenguin.parapgm.util.Chars;
import me.parapenguin.parapgm.util.ClassPathUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class ParaPGM extends JavaPlugin {

	@Getter public static String noPermissionsMessage = ChatColor.RED + "You do not have the required permissions to perform this action.";
	@Getter public static String noCommandMessage = ChatColor.RED + "You do not have the required permissions to use this command.";
	@Getter public static String noConsoleMessage = "Console, you can't use these commands!";
	@Getter public static String invalidCommandSyntax = ChatColor.RED + "[Error] Invalid command Syntax.";

	@Getter @Setter public static ParaPGM instance;
	@Getter @Setter public CommandsManager<CommandSender> commands;
	@Getter @Setter public static List<Class<?>> modules;

	@Getter public static File mapsRepository;
	@Getter public static File rotationsRepository;
	@Getter public static File libsRepository;

	@Getter public List<MapLoader> maps;

	public void onEnable() {
		setInstance(this);
		reloadConfig();
		setupCommands();

		StringBuilder sb = new StringBuilder();

		for (Chars character : Chars.values())
			sb.append(character.getUtf() + ",");

		Chars.allowCharacters(sb.toString().substring(0, sb.toString().length() - 1));

		maps = new ArrayList<MapLoader>();

		mapsRepository = getConfig().getString("repo.maps") != null
				? new File(getConfig().getString("repo.maps")) : new File("/parapgm/maps/");
		rotationsRepository = getConfig().getString("repo.rotations") != null
				? new File(getConfig().getString("repo.rotations")) : new File("/parapgm/rotations/");
		libsRepository = getConfig().getString("repo.libs") != null
				? new File(getConfig().getString("repo.libs")) : new File("/parapgm/libs/");

		getConfig().set("repo.maps", mapsRepository.getAbsolutePath());
		getConfig().set("repo.rotations", rotationsRepository.getAbsolutePath());

		ClassPathUtil.load(libsRepository);
		List<String> files = new ArrayList<String>();
		files.add("dom4j.jar");

		ClassPathUtil.load(libsRepository);
		if (!ClassPathUtil.addJars(files)) {
			ServerLog.severe("Failed to load all of the required libraries  Server shutting down!");
			getServer().shutdown();
			return;
		}

		if (!ClassPathUtil.loadJars()) {
			ServerLog.severe("Failed to add all of the required libraries to the class path  Server shutting down!");
			getServer().shutdown();
			return;
		}
		
		registerMapModules();
		InfoModule.load(ParaPGM.getInstance());

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
	}

	public void onDisable() {

	}

	@SuppressWarnings("resource")
	public void registerMapModules() {
		modules = new ArrayList<Class<?>>();
		String pkg = "me/parapenguin/parapgm/modules/map";

		List<String> names = new ArrayList<String>();

		for (File jar : getDataFolder().getParentFile().listFiles())
			if (jar.getName().endsWith(".jar"))
				if (jar.isDirectory()) {
					File subdir = new File(jar, pkg);
					if (!subdir.exists())
						continue;
					for (File file : subdir.listFiles()) {
						if (!file.isFile())
							continue;
					}
				} else {
					try {
						ZipFile zip = new ZipFile(jar);
						for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements(); ) {
							ZipEntry entry = entries.nextElement();
							String name = entry.getName();
							if (!name.startsWith(pkg))
								continue;
							name = name.substring(pkg.length() + 1);
							if (name.indexOf('/') < 0 && name.endsWith(".class"))
								names.add(name);
						}
					} catch (ZipException e) {
						getLogger().warning("Not a ZIP: " + e.getMessage());
					} catch (IOException e) {
						getLogger().warning(e.getMessage());
					}
				}

		for (String module : names) {
			String modulename = module.substring(0, module.length() - 6);
			//ServerLog.info("Found Class '" + module + "' or '" + modulename + "'");
			if (modulename.equalsIgnoreCase("Module")) continue;
			if(modulename.equalsIgnoreCase("Module")) continue;
			if(modulename.equalsIgnoreCase("BasicModule")) continue;
			if(!modulename.endsWith("Module")) continue;
			try {
				getLogger().info("Attempting to initialize module: " + modulename + ".");
				Class<?> moduleclass = Class.forName(pkg.replaceAll("/", ".") + "." + modulename);
				ParaPGM.modules.add(moduleclass);
			} catch (Exception ex) {
				getLogger().warning("Error initializing module: " + modulename + ".");
				ex.printStackTrace();
			}
		}

		List<Class<?>> loaded = new ArrayList<Class<?>>();
		for (Class<?> module : modules)
			try {
				//module.getMethod("load", MinecraftArcade.class).invoke(this, this);
				loaded.add(module);

				getLogger().info("Successfully loaded " + module.getSimpleName() + "!");
			} catch (Exception e) {
				e.printStackTrace();
			}

		getLogger().info(loaded.size() + " module" + (loaded.size() == 1 ? " has" : "s have") + " been loaded.");
		modules = loaded;

	}

	public void setupCommands() {
		commands = new CommandsManager<CommandSender>() {
			@Override
			public boolean hasPermission(CommandSender sender, String permission) {
				return sender instanceof ConsoleCommandSender || sender.hasPermission(permission);
			}
		};
		CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);
		cmdRegister.register(CommandFrameworkCommand.class);
		// Register Commands
	} 

	@Override
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

}
