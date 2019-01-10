package application.webscraper;

import javax.annotation.PostConstruct;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

@Service
public class WebScraperService {

	@PostConstruct
	public void scrape(){
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);

		/*
		Example link
		<a href="/wiki/Compiler" title="Compiler">
                compiled
              </a>
		 */
		try{
			HtmlPage page = client.getPage("https://en.wikipedia.org/wiki/Java_(programming_language)");
			System.out.println(page.getBody().asXml());


		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
