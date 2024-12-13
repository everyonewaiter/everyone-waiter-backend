package com.everyonewaiter.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RawPasswordEncoder implements Encoder<Encodable, Decodable> {

	private final PasswordEncoder passwordEncoder;

	@Override
	public String encode(Encodable encodable) {
		return passwordEncoder.encode(encodable.getRawText());
	}

	@Override
	public boolean matches(Encodable encodable, Decodable decodable) {
		return passwordEncoder.matches(encodable.getRawText(), decodable.getEncodedText());
	}
}
