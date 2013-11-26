package me.parapenguin.parapgm.command;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class MatchCommands {
	
	@Command(aliases = { "join" }, desc = "Join the match", usage = "[team] - The team to join", min = 0, max = 1)
	public static void join(final CommandContext args, CommandSender sender) throws CommandException {
		
	}

}
