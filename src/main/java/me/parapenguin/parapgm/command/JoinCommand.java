package me.parapenguin.parapgm.command;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class JoinCommand {
	
	@Command(aliases = { "join" }, desc = "Join the match", min = 0, max = 1)
	public static void join(final CommandContext args, CommandSender sender) throws CommandException {
		
	}

}
