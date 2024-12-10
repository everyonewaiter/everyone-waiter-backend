package com.everyonewaiter.security;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.everyonewaiter.fixture.security.DecodableBuilder;
import com.everyonewaiter.fixture.security.SecureEncodableBuilder;

class SignatureEncoderTest {

	private static final String VALID_KEY = "SECRET_KEY";
	private static final String INVALID_KEY = "INVALID_KEY";
	private static final String VALID_TEXT = "RAW_TEXT";
	private static final String INVALID_TEXT = "INVALID_TEXT";

	SignatureEncoder signatureEncoder = new SignatureEncoder();
	String signature = signatureEncoder.encode(new SecureEncodableBuilder().build());

	@DisplayName("평문 문자열을 서명한다.")
	@Test
	void signRawText() {
		SecureEncodable secureEncodable = new SecureEncodableBuilder().build();
		assertThat(signatureEncoder.encode(secureEncodable)).isEqualTo(signature);
	}

	@DisplayName("서명이 일치하는지 비교한다.")
	@MethodSource("matchesSignatureArgs")
	@ParameterizedTest(name = "비밀키: {0}, 메시지: {1}, 결과: {2}")
	void matchesSignature(String key, String rawText, boolean expected) {
		SecureEncodable encodable = new SecureEncodableBuilder().setSecretKey(key).setRawText(rawText).build();
		Decodable decodable = new DecodableBuilder().setEncodedText(signature).build();
		assertThat(signatureEncoder.matches(encodable, decodable)).isEqualTo(expected);
	}

	static Stream<Arguments> matchesSignatureArgs() {
		return Stream.of(
			Arguments.of(VALID_KEY, VALID_TEXT, true),
			Arguments.of(VALID_KEY, INVALID_TEXT, false),
			Arguments.of(INVALID_KEY, VALID_TEXT, false)
		);
	}
}
