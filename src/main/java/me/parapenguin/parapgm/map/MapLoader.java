package me.parapenguin.parapgm.map;

import me.parapenguin.parapgm.module.InfoModule;

import org.dom4j.Document;
import org.dom4j.Element;

public class MapLoader {
	
	Document document;
	
	InfoModule info;
	
	MapLoader(Document document, InfoModule info) {
		this.document = document;
		this.info = info;
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

}
