package com.everyonewaiter.user.application.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.everyonewaiter.fixture.user.EncodedPasswordBuilder;

class EncodedPasswordTest {

	@DisplayName("암호화된 비밀번호를 생성한다.")
	@Test
	void createEncodedPassword() {
		assertThatCode(() -> new EncodedPasswordBuilder().build()).doesNotThrowAnyException();
	}

	@DisplayName("암호화된 비밀번호는 공백을 허용하지 않는다.")
	@NullAndEmptySource
	@ValueSource(strings = {" ", "\t", "\n"})
	@ParameterizedTest(name = "[{index}] => 값: [{0}]")
	void doesNotBlank(String value) {
		EncodedPasswordBuilder encodedPasswordBuilder = new EncodedPasswordBuilder().setEncodedPassword(value);
		assertThatThrownBy(encodedPasswordBuilder::build).isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("getEncodedText() 메서드는 암호화된 비밀번호를 반환한다.")
	@Test
	void getEncodedText() {
		String expectedValue = "encodedPassword123";
		EncodedPassword encodedPassword = new EncodedPasswordBuilder().setEncodedPassword(expectedValue).build();

		String encodedText = encodedPassword.getEncodedText();

		assertThat(encodedText).isEqualTo(expectedValue);
	}
}
