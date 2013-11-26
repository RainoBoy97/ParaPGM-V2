package me.parapenguin.parapgm.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
	
	public abstract String name();
	
	public abstract String description();
	
	public abstract Class<? extends Module>[] requires();
	
}
