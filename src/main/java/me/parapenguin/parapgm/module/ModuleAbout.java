package me.parapenguin.parapgm.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleAbout {
	
	ModuleInfo info;
	List<Class<? extends Module>> requires;
	
	public ModuleAbout(Class<? extends Module> module) {
		this.info = ((ModuleInfo) module.getAnnotation(ModuleInfo.class));
		if(this.info == null) throw new IllegalStateException("ModuleInfo can't be null - Module must have a ModuleInfo annotation");
		
		this.requires = new ArrayList<Class<? extends Module>>();
		for(Class<? extends Module> clazz : info.requires())
			this.requires.add(clazz);
	}
	
	public ModuleInfo getInfo() {
		return info;
	}
	
	public String getName() {
		return info.name();
	}
	
	public String getDescription() {
		return info.description();
	}
	
	public List<Class<? extends Module>> getRequires() {
		return requires;
	}
	
}
