package com.hanium.smartdispenser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartDispenserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartDispenserApplication.class, args);
	}

}
