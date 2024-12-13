package com.everyonewaiter.user.application.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.everyonewaiter.fixture.user.RawPasswordBuilder;

class RawPasswordTest {

	@DisplayName("비밀번호를 생성한다.")
	@Test
	void createPassword() {
		assertThatCode(() -> new RawPasswordBuilder().build()).doesNotThrowAnyException();
	}

	@DisplayName("비밀번호의 형식이 옳바르지 않으면 예외가 발생한다.")
	@NullAndEmptySource
	@ValueSource(strings = {" ", "\t", "\n", "@short1", "password", "password1", "@password"})
	@ParameterizedTest(name = "[{index}] => 값: [{0}]")
	void invalidFormat(String value) {
		RawPasswordBuilder rawPasswordBuilder = new RawPasswordBuilder().setRawPassword(value);
		assertThatThrownBy(rawPasswordBuilder::build).isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("getRawText() 메서드는 비밀번호를 반환한다.")
	@Test
	void getRawText() {
		String expectedValue = "@password1";
		RawPassword rawPasswordBuilder = new RawPasswordBuilder().setRawPassword(expectedValue).build();

		String rawText = rawPasswordBuilder.getRawText();

		assertThat(rawText).isEqualTo(expectedValue);
	}
}
