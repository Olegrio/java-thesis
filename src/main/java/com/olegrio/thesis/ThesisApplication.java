package com.olegrio.thesis;

import com.olegrio.thesis.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThesisApplication.class, args);
	}

	@Autowired // (поле, конструктор, сеттер)
	public NewsService apiHttpClient;


}
