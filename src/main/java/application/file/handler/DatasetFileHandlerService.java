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
		File datasetDir = new File(System.getProperty("user.home") + storageDir);
		datasetDir.mkdir();

		File wordsDir = new File(datasetDir + "/" + WORDS_DIR + "/" + category);
		wordsDir.mkdirs();
		File linksDir = new File(datasetDir + "/" + LINKS_DIR + "/" + category);
		linksDir.mkdirs();

		System.out.println("Writing dataset files to: " + datasetDir.getAbsolutePath());

		for(ScrapedPage page : pages){
			try {
				PrintWriter wordContentWriter = new PrintWriter(wordsDir.getAbsolutePath() + "/" + page.getPage());
				wordContentWriter.println(page.getContent());
				wordContentWriter.close();

				PrintWriter linksWriter = new PrintWriter(linksDir.getAbsolutePath() + "/" + page.getPage());
				page.getOutgoingLinks().forEach(linksWriter::println);
				linksWriter.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
