package com.softuni.forwardingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForwardingCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForwardingCompanyApplication.class, args);
	}

}
