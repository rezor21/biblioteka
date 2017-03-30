package pl.rembisz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.jsondoc.spring.boot.starter.EnableJSONDoc;

@SpringBootApplication
@EnableJSONDoc
public class BibliotekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotekaApplication.class, args);
	}
}
