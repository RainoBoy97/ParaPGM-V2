package me.parapenguin.parapgm.modules;

import lombok.Getter;
import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.map.MapLoader;

import org.dom4j.Element;

public class BasicModule implements XMLModule {

	@Getter static protected ParaPGM instance;

	@Getter protected MapLoader loader;
	@Getter Element xml;

	public BasicModule(MapLoader loader) {
		this.loader = loader;
		this.xml = loader.getDocument().getRootElement();
	}

	public void parse() throws ModuleLoadException {
		// handled by the extending class
	}
	
	public static void load(ParaPGM instance) {
		BasicModule.instance = instance;
	}

	public boolean isLoaded() {
		return instance != null;
	}
	
	public boolean isUsed() {
		// handled by the extending class
		return false;
	}

	public class ModuleLoadException extends Exception {

		private static final long serialVersionUID = 8988149545837923802L;
		
		@Getter String reason;
		
		public ModuleLoadException(String reason) {
			this.reason = reason;
		}
		
	}
	
}
