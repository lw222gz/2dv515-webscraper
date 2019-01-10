package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebScraperApplication {


	public static void main(String[] args){
		SpringApplication.run(WebScraperApplication.class, args);
		System.out.println("Hello. This is application");
		System.out.println(System.getProperty("origin"));
		System.out.println(System.getProperties().size());
	}
}
