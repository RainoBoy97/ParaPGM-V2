package me.parapenguin.parapgm.commands;

import java.util.Map;


import me.parapenguin.parapgm.ParaPGM;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class CommandFrameworkCommand {
	
	@Command(aliases = { "cmdf", "framework", "commandframework" }, desc = "View current Commands", usage = "- Allows view of commands", min = 0, max = 2)
	public static void framework(final CommandContext args, CommandSender sender) throws CommandException {
		ParaPGM instance = ParaPGM.getInstance();
		if(args.getString(0).equalsIgnoreCase("search")) {
			String search = args.getString(1);
			Map<String, String> commands = instance.getCommands().getCommands();
		} else {
			if(args.argsLength() == 0) {
				sender.sendMessage(ChatColor.GOLD + "Found a total of " + instance.getCommands().getCommands().size() + " commands.");
			} else {
				sender.sendMessage(ParaPGM.getInvalidCommandSyntax());
				sender.sendMessage(ChatColor.RED + "/cmdf <search> <searchfor>");
			}
		}
	}

}
