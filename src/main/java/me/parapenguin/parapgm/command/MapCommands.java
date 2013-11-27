package me.parapenguin.parapgm.command;

import java.util.ArrayList;
import java.util.List;

import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.util.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class MapCommands {
	
	@Command(aliases = { "map" }, desc = "View information on the current map or map supplied", usage = "[map]")
	public static void map(final CommandContext args, CommandSender sender) throws CommandException {
		
	}
	
	@Command(aliases = { "maps" }, desc = "View all the currently loaded maps", usage = "[page]", min = 0, max = 1)
	public static void maps(final CommandContext args, CommandSender sender) throws CommandException {
		String bar = ChatColor.RED + " ----------- ";
		String loaded = ChatColor.DARK_AQUA + "Loaded Maps (" + ChatColor.AQUA + "[page]" + ChatColor.DARK_AQUA
				+ " of " + ChatColor.AQUA + "[pages]" + ChatColor.DARK_AQUA + ")";
		
		String header = bar + loaded + bar;
		List<String> rows = new ArrayList<String>();
		for(MapLoader loader : ParaPGM.getMaps()) {
			rows.add(ChatColor.GOLD + loader.getName()
					+ " " + ChatColor.DARK_PURPLE + "by "
					+ StringUtils.commaList(ChatColor.RED, loader.getAuthorNames(), ChatColor.DARK_PURPLE));
		}
		int results = 8;
		boolean numbered = true;
		
		int page = 1;
		if(args.argsLength() == 1)
			page = args.getInteger(0);
		
		PaginatedResult result = new PaginatedResult(header, rows, results, numbered);
		result.display(sender, page);
	}

}
