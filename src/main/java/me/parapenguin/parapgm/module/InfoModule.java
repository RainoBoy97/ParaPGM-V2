package me.parapenguin.parapgm.module;

import java.util.ArrayList;
import java.util.List;

public class InfoModule {
	
	String name;
	String version;
	
	List<Contributor> authors;
	List<Contributor> contributors;
	
	InfoModule(String name, String version) {
		this(name, version, new ArrayList<Contributor>(), new ArrayList<Contributor>());
	}
	
	InfoModule(String name, String version, List<Contributor> authors, List<Contributor> contributors) {
		this.name = name;
		this.version = version;
		this.authors = authors;
		this.contributors = contributors;
	}
	
	public String getName() {
		return name;
	}
	
	public String getVersion() {
		return version;
	}
	
	public List<Contributor> getAuthors() {
		return authors;
	}
	
	public class Contributor {
		
		String username;
		String contribution;
		
		public Contributor(String name, String contribution) {
			this.username = name;
			this.contribution = contribution;
		}
		
		public boolean hasContribution() {
			return contribution != null;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getContribution() {
			return contribution;
		}
		
	}
	
}
