package com.everyonewaiter.user.application.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.everyonewaiter.fixture.user.PhoneNumberBuilder;

class PhoneNumberTest {

	@DisplayName("휴대폰 번호를 생성한다.")
	@Test
	void createPhoneNumber() {
		assertThatCode(() -> new PhoneNumberBuilder().build()).doesNotThrowAnyException();
	}

	@DisplayName("휴대폰 번호의 형식이 옳바르지 않으면 예외가 발생한다.")
	@NullAndEmptySource
	@ValueSource(strings = {" ", "\t", "\n", "010-1234-5678", "010123456789", "010123-4567"})
	@ParameterizedTest(name = "[{index}] => 값: [{0}]")
	void invalidFormat(String value) {
		PhoneNumberBuilder phoneNumberBuilder = new PhoneNumberBuilder().setPhoneNumber(value);
		assertThatThrownBy(phoneNumberBuilder::build).isInstanceOf(IllegalArgumentException.class);
	}
}
