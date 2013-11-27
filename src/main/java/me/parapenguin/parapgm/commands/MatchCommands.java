package me.parapenguin.parapgm.commands;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class MatchCommands {
	
	@Command(aliases = { "join" }, desc = "Join the match", usage = "[team]", min = 0, max = 1)
	public static void join(final CommandContext args, CommandSender sender) throws CommandException {
		
	}
	
	@Command(aliases = { "match" }, desc = "View match information", min = 0, max = 0)
	public static void match(final CommandContext args, CommandSender sender) throws CommandException {
		
	}
	
	@Command(aliases = { "map" }, desc = "View information on the current map or map supplied", usage = "[map]")
	public static void map(final CommandContext args, CommandSender sender) throws CommandException {
		
	}

}
