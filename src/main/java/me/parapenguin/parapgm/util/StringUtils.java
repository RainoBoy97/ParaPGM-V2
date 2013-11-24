package me.parapenguin.parapgm.util;

import org.bukkit.ChatColor;

public class StringUtils {
	
	public static ChatColor convertStringToChatColor(String string) {
		for(ChatColor color : ChatColor.values())
			if(color.name().replaceAll("_", " ").equalsIgnoreCase(string) || color.name().equalsIgnoreCase(string))
				return color;
		
		return null;
	}
	
	public static Integer convertStringToInteger(String string) throws NumberFormatException {
		return Integer.parseInt(string);
	}
	
	public static Integer convertStringToInteger(String string, int fallback) {
		try {
			return convertStringToInteger(string);
		} catch(NumberFormatException e) {
			return fallback;
		}
	}
	
}
