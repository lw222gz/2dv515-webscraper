package application.webscraper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;

import application.objects.ScrapedPage;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class WebScraperService {

	private static final String WIKIPEDIA_BASE_PATH = "https://en.wikipedia.org";

	private static final String CATLINKS_CLASS = "catlinks";
	private static final String FOOTER_CLASS = "printfooter";
	private static final String JUMP_LINKS_CLASS = "mw-jump-link";
	private static final String EDIT_SECTION_CLASS = "mw-editsection";
	private static final String TABLE_OF_CONTENTS_CLASS = "toc";
	private static final String WIKIPEDIA_SITE_SUB_ID = "siteSub";

	private static final Pattern WIKI_LINK_PATTERN = Pattern.compile("^/wiki/*");

	private Set<String> scrapedPages;
	private Set<String> allPages;

	@PostConstruct
	void init(){
		scrapedPages = new HashSet<>();
		allPages = new HashSet<>();
	}

	/**
	 * Will scrape 200 pages, starting from the given root page and and then continuing on other random pages
	 * linked from the root page. After successfully scraping a page it picks a random page that has
	 * been linked in any scraped page.
	 * @param rootPage Suffix string of wiki URL, e.g. /wiki/Java_(programming_language)
	 */
	public List<ScrapedPage> scrape(String rootPage){
		List<ScrapedPage> categoryPages = new ArrayList<>();
		allPages.add(rootPage);

		try{

			for(int i = 0; i < 200; i++){

				String wikiPage = getRandomUnscrapedPage();
				Document doc = Jsoup.connect(WIKIPEDIA_BASE_PATH + wikiPage).get();

				Element content = doc.getElementById("content");

				//Removes unwanted stuff for dataset
				content.getElementById("bodyContent").getElementsByClass(CATLINKS_CLASS).remove();
				content.getElementById("bodyContent").getElementsByClass(FOOTER_CLASS).remove();
				content.getElementById("bodyContent").getElementsByClass(JUMP_LINKS_CLASS).remove();
				content.getElementById("bodyContent").getElementsByClass(EDIT_SECTION_CLASS).remove();
				content.getElementById("bodyContent").getElementsByClass(TABLE_OF_CONTENTS_CLASS).remove();
				content.getElementById("bodyContent").getElementById(WIKIPEDIA_SITE_SUB_ID).remove();

				ScrapedPage page = new ScrapedPage(wikiPage.replace("/wiki/", "").replace("/", "057"), content.text()
						.toLowerCase()
						.replaceAll("[^a-zA-Z0-9]", " ")
						.replaceAll(" +", " "));

				page.addLinks(getPageLinks(content.getElementById("bodyContent")));

				categoryPages.add(page);

				System.out.println("Iteration : " + i + 1 + " Successfully scraped: " + page.getPage());
			}

		} catch (Exception e){
			e.printStackTrace();
		}

		return categoryPages;
	}

	private String getRandomUnscrapedPage() {
		Collection<String> unscrapedPages = CollectionUtils.disjunction(allPages, scrapedPages);
		int size = unscrapedPages.size();

		if(size == 0){
			throw new RuntimeException("Did not find enough outgoing links to scrape 200 pages.");
		}

		Random rng = new Random();
		int randomIndex = rng.nextInt(size);
		String randomPage = unscrapedPages.toArray(new String[size])[randomIndex];
		scrapedPages.add(randomPage);
		return randomPage;
	}


	private Collection<String> getPageLinks(Element content) {
		Set<String> links = new HashSet<>();

		content.getElementsByTag("a")
				.forEach(s -> {
					String link = s.attributes().get("href");
					if(WIKI_LINK_PATTERN.matcher(link).find() && !link.contains(":")){
						links.add(link);
					}
				});

		allPages.addAll(links);
		return links;
	}
}
