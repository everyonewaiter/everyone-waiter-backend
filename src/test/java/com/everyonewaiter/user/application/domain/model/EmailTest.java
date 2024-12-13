package com.everyonewaiter.user.application.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.everyonewaiter.fixture.user.EmailBuilder;

class EmailTest {

	@DisplayName("이메일을 생성한다.")
	@Test
	void createEmail() {
		assertThatCode(() -> new EmailBuilder().build()).doesNotThrowAnyException();
	}

	@DisplayName("이메일의 형식이 옳바르지 않으면 예외가 발생한다.")
	@NullAndEmptySource
	@ValueSource(strings = {" ", "\t", "\n", "handwoong@gmail", "handwoong@gmail.*", "handwoong"})
	@ParameterizedTest(name = "[{index}] => 값: [{0}]")
	void invalidFormat(String value) {
		EmailBuilder emailBuilder = new EmailBuilder().setEmail(value);
		assertThatThrownBy(emailBuilder::build).isInstanceOf(IllegalArgumentException.class);
	}
}
