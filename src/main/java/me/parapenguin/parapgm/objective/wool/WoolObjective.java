package me.parapenguin.parapgm.objective.wool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.dom4j.Document;

import me.parapenguin.parapgm.map.exception.ModuleLoadException;
import me.parapenguin.parapgm.module.Module;
import me.parapenguin.parapgm.module.ModuleAbout;
import me.parapenguin.parapgm.objective.ObjectiveModule;
import me.parapenguin.parapgm.player.AboutPlayer;
import me.parapenguin.parapgm.team.MatchTeam;

public class WoolObjective implements ObjectiveModule {
	
	MatchTeam team;
	DyeColor dye;
	ChatColor chat;
	
	boolean complete;
	
	WoolObjective(MatchTeam team, DyeColor dye, ChatColor chat) {
		this.team = team;
		this.dye = dye;
		this.chat = chat;
		
		this.complete = false;
	}

	public void complete(AboutPlayer player) {
		
	}

	public boolean isComplete() {
		return complete;
	}

	public MatchTeam getTeam() {
		return team;
	}
	
	public static List<WoolObjective> parse() {
		List<WoolObjective> wools = new ArrayList<WoolObjective>();
		
		return null;
	}

	@Override
	public ModuleAbout getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Module parse(Document document) throws ModuleLoadException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
