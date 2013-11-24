package me.parapenguin.parapgm.map;

import java.io.File;
import java.util.List;

import me.parapenguin.parapgm.module.InfoModule;

import org.dom4j.Document;
import org.dom4j.Element;

public class MapLoader {
	
	Document document;
	
	File folder;
	InfoModule info;
	
	MapLoader(File folder, Document document, InfoModule info) {
		this.folder = folder;
		this.document = document;
		this.info = info;
	}
	
	public Map getMap() {
		//TODO: Create a way to create Map objects
		return null;
	}
	
	public Document getDocument() {
		return document;
	}
	
	public Element getRoot() {
		return document.getRootElement();
	}
	
	public String getName() {
		return info.getName();
	}
	
	public String getFolderName() {
		return folder.getName();
	}
	
	public static MapLoader isThis(String string, List<MapLoader> loaders) {
		for(MapLoader loader : loaders)
			if(loader.getName().equalsIgnoreCase(string))
				return loader;
		
		for(MapLoader loader : loaders)
			if(loader.getName().toLowerCase().contains(string.toLowerCase()))
				return loader;
		
		return null;
	}
	
	public static MapLoader getLoader(File xml, File region, File level) {
		File folder = xml.getParentFile();
	}

}
