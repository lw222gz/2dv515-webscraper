package application.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScrapedPage {
	private String content;
	private String page;
	private List<String> outgoingLinks;

	public ScrapedPage(String page, String content){
		this.content = content;
		this.page = page;
		outgoingLinks = new ArrayList<>();
	}

	public List<String> getOutgoingLinks() {
		return outgoingLinks;
	}

	public String getPage() {
		return page;
	}

	public String getContent() {
		return content;
	}

	public void addLinks(Collection<String> links){
		outgoingLinks.addAll(links);
	}
}
