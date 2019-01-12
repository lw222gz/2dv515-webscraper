package application;

import static java.util.Objects.isNull;

import java.util.List;

import application.file.handler.DatasetFileHandlerService;
import application.objects.ScrapedPage;
import application.webscraper.WebScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class WebScraperApplication {

	@Autowired
	private WebScraperService webScraperService;

	@Autowired
	private DatasetFileHandlerService datasetFileHandlerService;

	public static void main(String[] args){
		SpringApplication.run(WebScraperApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init(){
		String rootPage = System.getProperty("root.page");
		String category = System.getProperty("category");
		String datasetDirectory = System.getProperty("dataset.dir");

		System.out.println("Here");
		System.out.println(rootPage);
		System.out.println(category);
		System.out.println(datasetDirectory);

		if(isNull(rootPage)){
			throw new RuntimeException("Missing a root page.");
		}
		if(isNull(category)){
			throw new RuntimeException("Missing a category.");
		}
		if(isNull(datasetDirectory)){
			throw new RuntimeException("Missing a directory to store dataset files.");
		}

		List<ScrapedPage> categoryPages = webScraperService.scrape(rootPage);
		datasetFileHandlerService.storeScrapedPages(categoryPages, category, datasetDirectory);
	}
}
