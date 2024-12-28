package com.everyonewaiter.authentication.application.domain.model;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.everyonewaiter.fixture.authentication.AuthenticationCodeBuilder;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

class AuthenticationCodeTest {

	@DisplayName("create 메서드로 사용자 인스턴스를 생성하면 인증 번호 생성 이벤트를 등록한다.")
	@Test
	void create() {
		AuthenticationCode authenticationCode =
			AuthenticationCode.create(new PhoneNumberBuilder().build(), AuthenticationPurpose.USER_SIGN_UP, 123456, 0L);
		assertThat(authenticationCode.domainEvents()).hasSize(1);
	}

	@DisplayName("만료 시간을 제외한 나머지 값이 같다면 동등성 비교 시 True를 반환한다.")
	@Test
	void equals() {
		AuthenticationCode source = new AuthenticationCodeBuilder().setExpiration(0L).build();
		AuthenticationCode target = new AuthenticationCodeBuilder().setExpiration(1L).build();
		assertThat(source.equals(target)).isTrue();
	}

	@DisplayName("만료 시간을 제외한 나머지 값이 다르다면 동등성 비교 시 False를 반환한다.")
	@MethodSource("notEqualsArgs")
	@ParameterizedTest(name = "휴대폰 번호: {0}, 인증 목적: {1}, 인증 번호: {2}")
	void notEquals(String phoneNumber, AuthenticationPurpose authenticationPurpose, Integer authenticationCode) {
		AuthenticationCode source = new AuthenticationCodeBuilder().setExpiration(0L).build();
		AuthenticationCode target = new AuthenticationCodeBuilder()
			.setPhoneNumber(new PhoneNumber(phoneNumber))
			.setPurpose(authenticationPurpose)
			.setCode(authenticationCode)
			.setExpiration(1L)
			.build();
		assertThat(source.equals(target)).isFalse();
	}

	static Stream<Arguments> notEqualsArgs() {
		return Stream.of(
			Arguments.of("01012345678", AuthenticationPurpose.USER_SIGN_UP, 654321),
			Arguments.of("01012345678", AuthenticationPurpose.DEVICE_AUTHENTICATION, 123456),
			Arguments.of("01087654321", AuthenticationPurpose.USER_SIGN_UP, 123456)
		);
	}
}
