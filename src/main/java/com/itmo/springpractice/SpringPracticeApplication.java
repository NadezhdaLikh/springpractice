package com.itmo.springpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringPracticeApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringPracticeApplication.class, args);
	}
}
