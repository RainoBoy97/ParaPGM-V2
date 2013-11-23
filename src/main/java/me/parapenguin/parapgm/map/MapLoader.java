package me.parapenguin.parapgm.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.modules.XMLModule;
import me.parapenguin.parapgm.modules.BasicModule.ModuleLoadException;
import me.parapenguin.parapgm.modules.map.InfoModule;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class MapLoader {

    @Getter Document document;
    @Getter File xml;
    @Getter File region;
    @Getter File level;

    @Getter InfoModule info;
	
	public static MapLoader getLoader(File xml, File region, File level) throws MapLoadException {
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			doc = reader.read(xml);
		} catch (DocumentException e) {
			ParaPGM.getInstance().getLogger().info("File fired DocumentException when trying to getLoader() - return null;");
			return null;
		}
		
		if(doc == null)
			ParaPGM.getInstance().getLogger().info("Document is null?");
		return new MapLoader(doc, xml, region, level);
	}
	
	private MapLoader(Document document, File xml, File region, File level) throws MapLoadException {
		this.document = document;
		this.xml = xml;
		this.region = region;
		this.level = level;
		
		this.info = new InfoModule(this);
		
		try {
			this.info.parse();
		} catch(ModuleLoadException e) {
			throw new MapLoadException("Could not load some maps required Modules - " + e.getReason());
		}
	}
	
	public Map getMap() {
		List<XMLModule> modules = new ArrayList<XMLModule>();
		for(Class<?> clazz : ParaPGM.getModules()) {
			XMLModule module;
			try { module = (XMLModule) clazz.getConstructor(MapLoader.class).newInstance(this); } catch (Exception e) { continue; }
			
			try { module.parse(); } catch (ModuleLoadException e) {
				e.printStackTrace();
				continue;
			}
			
			modules.add(module);
		}
		
		Map map = new Map(info, modules);
		return map;
	}
	
	public class MapLoadException extends Exception {
		
		private static final long serialVersionUID = 6467822400246228633L;
		
		@Getter String reason;
		
		public MapLoadException(String reason) {
			this.reason = reason;
		}
	}
	
}
