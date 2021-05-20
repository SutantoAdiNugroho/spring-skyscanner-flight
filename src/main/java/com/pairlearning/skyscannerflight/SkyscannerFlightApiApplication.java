package com.pairlearning.skyscannerflight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SkyscannerFlightApiApplication {

	@Bean
	public RestTemplate execRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SkyscannerFlightApiApplication.class, args);
	}

}
