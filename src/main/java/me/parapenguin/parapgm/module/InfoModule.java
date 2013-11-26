package me.parapenguin.parapgm.module;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "InfoModule", description = "Contains information on the map", requires = {})
public class InfoModule {
	
	String name;
	String version;
	String objective;
	
	List<Contributor> authors;
	List<Contributor> contributors;
	
	InfoModule(String name, String version, String objective) {
		this(name, version, objective, new ArrayList<Contributor>(), new ArrayList<Contributor>());
	}
	
	InfoModule(String name, String version, String objective, List<Contributor> authors, List<Contributor> contributors) {
		this.name = name;
		this.version = version;
		this.objective = objective;
		this.authors = authors;
		this.contributors = contributors;
	}
	
	public String getName() {
		return name;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getObjective() {
		return objective;
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
