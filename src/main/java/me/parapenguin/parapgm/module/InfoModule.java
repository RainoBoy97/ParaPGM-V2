package me.parapenguin.parapgm.module;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import me.parapenguin.parapgm.map.exception.ModuleLoadException;

@ModuleInfo(name = "InfoModule", description = "Contains information on the map", requires = {})
public class InfoModule implements Module {
	
	String name;
	String version;
	String objective;
	
	List<Contributor> authors;
	List<Contributor> contributors;
	
	boolean usable = false;
	
	public InfoModule() {}
	
	InfoModule(String name, String version, String objective) {
		this(name, version, objective, new ArrayList<Contributor>(), new ArrayList<Contributor>());
	}
	
	InfoModule(String name, String version, String objective, List<Contributor> authors, List<Contributor> contributors) {
		this.name = name;
		this.version = version;
		this.objective = objective;
		this.authors = authors;
		this.contributors = contributors;
		
		this.usable = true;
	}
	
	public boolean isUsable() {
		return usable;
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
	
	public List<Contributor> getContributors() {
		return contributors;
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

	@Override
	public ModuleAbout getInfo() {
		return new ModuleAbout(this.getClass());
	}

	@Override
	public Module parse(Document document) throws ModuleLoadException {
		if(isUsable()) {
			throw new ModuleLoadException("The Module '" + getInfo().getName() + "' wasn't a usable object");
		}
		
		Element root = document.getRootElement();
		String name = root.element("name").getText();
		if(name == null)
			throw new ModuleLoadException("Map names can't be null");

		String version = root.element("version").getText();
		if(version == null)
			throw new ModuleLoadException("Map versions can't be null");

		String objective = root.element("objective").getText();
		if(objective == null)
			throw new ModuleLoadException("Map objectives can't be null");
		
		List<Contributor> authors = new ArrayList<Contributor>();
		Element authorsElement = root.element("authors");
		for(Object object : authorsElement.elements("author")) {
			if(object instanceof Element == false) continue;
			Element author = (Element) object;

			if(author.getText() == null) continue;
			String contrib = author.attributeValue("contribution");
			authors.add(new Contributor(author.getText(), contrib));
		}
		
		if(authors.size() == 0)
			throw new ModuleLoadException("Maps must have at least 1 author");
		
		List<Contributor> contributors = new ArrayList<Contributor>();
		Element contributorsElement = root.element("contributors");
		if(contributorsElement != null)
			for(Object object : contributorsElement.elements("contributor")) {
				if(object instanceof Element == false) continue;
				Element contributor = (Element) object;
				
				if(contributor.getText() == null) continue;
				String contrib = contributor.attributeValue("contribution");
				contributors.add(new Contributor(contributor.getText(), contrib));
			}
		
		InfoModule info = new InfoModule(name, version, objective, authors, contributors);
		return info;
	}
	
}
