package me.parapenguin.parapgm.map;

import me.parapenguin.parapgm.module.InfoModule;

import org.dom4j.Document;
import org.dom4j.Element;

public class MapLoader {
	
	Document document;
	Element root;
	
	InfoModule info;
	
	MapLoader(Document document, InfoModule info) {
		this.document = document;
		this.root = document.getRootElement();
		this.info = info;
	}

}
