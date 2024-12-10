package com.everyonewaiter.fixture.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

	private static final String PREFIX = "{encode}";

	@Override
	public String encode(CharSequence rawPassword) {
		return PREFIX + rawPassword;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.replace(PREFIX, "").contentEquals(rawPassword);
	}
}
