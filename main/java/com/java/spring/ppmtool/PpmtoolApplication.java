package com.java.spring.ppmtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PpmtoolApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(PpmtoolApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bcrypt() {
		return new BCryptPasswordEncoder();
	}
}