package me.parapenguin.parapgm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PaginatedResult {
	
	String header;
	List<String> rows;
	int results;
	boolean number;
	
	public PaginatedResult(String header, List<String> rows, int results, boolean number) {
		this.header = header;
		this.rows = rows;
		this.results = results;
		this.number = number;
	}
	
	public int getPages() {
		if(getRows().size() == 0) return 0;
		
		if(getRows().size() % results == 0) return getRows().size() / results;
		
		return ((getRows().size() - (getRows().size() % results)) / results) + 1;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getHeader(int page) {
		return header.replace("[page]", page + "").replace("[pages]", getPages() + "");
	}
	
	public List<String> getRows() {
		return rows;
	}
	
	public List<String> getRows(int page) {
		List<String> rows = new ArrayList<String>();
		
		/*
			
			0-[results]
			[results * (page - 1)] -> [results * page]
			
		 */
		
		int start = getRows().size() * (page - 1);
		int finish = results * page;
		
		if(finish >= getRows().size())
			finish = getRows().size() - 1;
		
		rows = getRows().subList(start, finish);
		
		return rows;
	}
	
	public void display(CommandSender sender) {
		display(sender, 1);
	}
	
	public void display(CommandSender sender, int page) {
		List<String> rows = getRows(page);
		
		sender.sendMessage(getHeader(page));
		
		int i = 1;
		for(String row : rows) {
			if(number) row = ChatColor.WHITE + "" + i + ". " + row;
			sender.sendMessage(row);
			i++;
		}
	}
	
}
