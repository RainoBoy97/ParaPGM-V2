package me.parapenguin.parapgm.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.map.exception.MapLoadException;
import me.parapenguin.parapgm.map.exception.ModuleLoadException;
import me.parapenguin.parapgm.module.InfoModule;
import me.parapenguin.parapgm.module.InfoModule.Contributor;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
	
	public List<Contributor> getAuthors() {
		return info.getAuthors();
	}
	
	public List<String> getAuthorNames() {
		List<String> names = new ArrayList<String>();
		for(Contributor contrib : getAuthors())
			names.add(contrib.getUsername());
		return names;
	}
	
	public List<Contributor> getContributors() {
		return info.getContributors();
	}
	
	public String getFolderName() {
		return folder.getName();
	}
	
	public boolean isThis(String string, List<MapLoader> loaders) {
		for(MapLoader loader : loaders)
			if(loader.getName().equalsIgnoreCase(string))
				return true;
		
		for(MapLoader loader : loaders)
			if(loader.getName().toLowerCase().contains(string.toLowerCase()))
				return true;
		
		return false;
	}
	
	public static MapLoader getLoader(File xml, File region, File level) throws ModuleLoadException, MapLoadException {
		File folder = xml.getParentFile();
		
		SAXReader reader = new SAXReader();
		
		Document document;
		try {
			document = reader.read(xml);
		} catch (DocumentException e) {
			throw new MapLoadException("Could not load Map due to DocumentException being thrown");
		}
		
		InfoModule info = (InfoModule) new InfoModule().parse(document);
		
		//ParaPGM.getLog().info("Found map: xml[" + xml.getPath() + "], region[" + region.getPath() + "], level[" + level.getPath() + "]");
		return new MapLoader(folder, document, info);
	}
	
	public static MapLoader getLoader(String name) {
		for(MapLoader loader : ParaPGM.getMaps())
			if(loader.isThis(name, ParaPGM.getMaps()))
				return loader;
		
		return null;
	}

}
