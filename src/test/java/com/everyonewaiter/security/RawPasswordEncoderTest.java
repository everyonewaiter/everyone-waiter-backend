package com.everyonewaiter.security;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.everyonewaiter.fixture.security.DecodableBuilder;
import com.everyonewaiter.fixture.security.EncodableBuilder;
import com.everyonewaiter.fixture.security.FakePasswordEncoder;

class RawPasswordEncoderTest {

	private static final String RAW_TEXT = "RAW_TEXT";
	private static final String INVALID_TEXT = "INVALID_TEXT";
	private static final String ENCODED_PASSWORD = "{encode}" + RAW_TEXT;

	RawPasswordEncoder rawPasswordEncoder = new RawPasswordEncoder(new FakePasswordEncoder());

	@DisplayName("비밀번호를 암호화한다.")
	@Test
	void encodePassword() {
		Encodable encodable = new EncodableBuilder().build();

		String actual = rawPasswordEncoder.encode(encodable);

		assertThat(actual).isEqualTo(ENCODED_PASSWORD);
	}

	@DisplayName("평문 비밀번호와 암호화된 비밀번호를 비교한다.")
	@MethodSource("matchesPasswordArgs")
	@ParameterizedTest(name = "평문: {0}, 결과: {1}")
	void matchesPassword(String rawText, boolean expected) {
		Encodable encodable = new EncodableBuilder().setRawText(rawText).build();
		Decodable decodable = new DecodableBuilder().setEncodedText(ENCODED_PASSWORD).build();
		Assertions.assertThat(rawPasswordEncoder.matches(encodable, decodable)).isEqualTo(expected);
	}

	static Stream<Arguments> matchesPasswordArgs() {
		return Stream.of(
			Arguments.of(RAW_TEXT, true),
			Arguments.of(INVALID_TEXT, false)
		);
	}
}
