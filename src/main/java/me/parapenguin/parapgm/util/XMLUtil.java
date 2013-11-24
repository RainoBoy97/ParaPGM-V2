package me.parapenguin.parapgm.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class XMLUtil {
	
	public static List<Element> getElements(Element root, String name) {
		List<Element> elements = new ArrayList<Element>();
		
		if(root == null)
			return elements;
		
		for(Object object : root.elements(name))
			if(object instanceof Element)
				elements.add((Element) object);
		
		return elements;
	}
	
	public static List<Element> getElements(Element root) {
		List<Element> elements = new ArrayList<Element>();
		
		if(root == null)
			return elements;
		
		for(Object object : root.elements())
			if(object instanceof Element)
				elements.add((Element) object);
		
		return elements;
	}
	
}
