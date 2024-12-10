package com.everyonewaiter.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class P6SpyConfiguration {

	@Bean
	P6SpyEventListener p6spyEventListener() {
		return new P6SpyEventListener();
	}

	@Bean
	P6SpyFormatter p6spyFormatter() {
		return new P6SpyFormatter();
	}
}
