package me.parapenguin.parapgm.command;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class MapCommands {
	
	@Command(aliases = { "join" }, desc = "Join the match", usage = "[team] - The team to join", min = 0, max = 1)
	public static void join(final CommandContext args, CommandSender sender) throws CommandException {
		
	}
	
	@Command(aliases = { "match" }, desc = "View match information", min = 0, max = 0)
	public static void match(final CommandContext args, CommandSender sender) throws CommandException {
		
	}
	
	@Command(aliases = { "map" }, desc = "View information on the current map or map supplied", usage = "[map] - Map to view")
	public static void map(final CommandContext args, CommandSender sender) throws CommandException {
		
	}

}
