package com.everyonewaiter.security;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "allow.client")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ClientOriginRegistry {

	private final List<String> urls;

	List<String> getUrls() {
		return Collections.unmodifiableList(urls);
	}
}