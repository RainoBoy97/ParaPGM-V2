package me.parapenguin.parapgm.modules.map;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.parapenguin.parapgm.map.MapLoader;
import me.parapenguin.parapgm.modules.BasicModule;
import me.parapenguin.parapgm.player.Client;

import org.dom4j.Element;

public class InfoModule extends BasicModule {

	@Getter String name;
	@Getter String version;
	@Getter List<Contributor> authors;
	@Getter List<Contributor> contibutors;

	public InfoModule(MapLoader loader) {
		super(loader);
	}

	@Override
	public void parse() throws ModuleLoadException {
		Element root = loader.getDocument().getRootElement();
		String name = root.element("name").getText();
		if(name == null)
			throw new ModuleLoadException("Map names can't be null");

		String version = root.element("version").getText();
		if(version == null)
			throw new ModuleLoadException("Map versions can't be null");
		
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
		for(Object object : contributorsElement.elements("contributor")) {
			if(object instanceof Element == false) continue;
			Element contributor = (Element) object;
			
			if(contributor.getText() == null) continue;
			String contrib = contributor.attributeValue("contribution");
			contributors.add(new Contributor(contributor.getText(), contrib));
		}
		
		this.name = name;
		this.version = version;
		this.authors = authors;
		this.contibutors = contributors;
	}

	public class Contributor {

		@Getter String name;
		@Getter String contribution;

		public Contributor(String name, String contribution) {
			this.name = name;
			this.contribution = contribution;
		}

		public boolean isOnline() {
			return Client.getClient(name) != null;
		}

	}


}
