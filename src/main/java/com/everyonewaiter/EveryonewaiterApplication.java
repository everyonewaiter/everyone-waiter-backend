package com.everyonewaiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.everyonewaiter")
public class EveryonewaiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveryonewaiterApplication.class, args);
	}
}
