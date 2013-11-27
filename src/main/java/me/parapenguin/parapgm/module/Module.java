package me.parapenguin.parapgm.module;

import me.parapenguin.parapgm.map.exception.ModuleLoadException;

import org.dom4j.Document;

public interface Module {
	
	public ModuleAbout getInfo();
	
	public boolean isUsable();
	
	public Module parse(Document document) throws ModuleLoadException;
	
}
