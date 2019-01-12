package application.file.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import application.objects.ScrapedPage;
import org.springframework.stereotype.Service;

@Service
public class DatasetFileHandlerService {

	private static final String WORDS_DIR = "Words";
	private static final String LINKS_DIR = "Links";

	public void storeScrapedPages(List<ScrapedPage> pages, String category, String storageDir){
		File categoryDir = new File(System.getProperty("user.home") + storageDir + "/" + category);
		categoryDir.mkdir();

		File wordsDir = new File(categoryDir + "/" + WORDS_DIR);
		wordsDir.mkdir();
		File linksDir = new File(categoryDir + "/" + LINKS_DIR);
		linksDir.mkdir();

		System.out.println(categoryDir.getAbsolutePath());

		for(ScrapedPage page : pages){
			try {
				PrintWriter wordContentWriter = new PrintWriter(wordsDir.getAbsolutePath() + "/" + page.getPage());
				wordContentWriter.println(page.getContent());

				PrintWriter linksWriter = new PrintWriter(linksDir.getAbsolutePath() + "/" + page.getPage());
				page.getOutgoingLinks().forEach(linksWriter::println);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
