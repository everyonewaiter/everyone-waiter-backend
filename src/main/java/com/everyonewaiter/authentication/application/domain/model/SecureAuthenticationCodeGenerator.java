package com.everyonewaiter.authentication.application.domain.model;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class SecureAuthenticationCodeGenerator implements AuthenticationCodeGenerator {

	private static final int MIN = 100_000;
	private static final int MAX = 999_999;

	@Override
	public Integer generate() {
		return new SecureRandom().nextInt(MAX - MIN + 1) + MIN;
	}
}
