package com.everyonewaiter.authentication.application.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCount;
import com.everyonewaiter.fixture.authentication.AuthenticationAttemptCountBuilder;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.port.out.UserExistsPort;

@ExtendWith(MockitoExtension.class)
class UserSignUpStrategyTest {

	@Mock
	UserExistsPort userExistsPort;

	@InjectMocks
	UserSignUpStrategy userSignUpStrategy;

	@DisplayName("휴대폰 번호가 존재하지 않으면 검증에 성공한다.")
	@Test
	void validatePhoneNumber() {
		PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
		when(userExistsPort.exists(any(PhoneNumber.class))).thenReturn(false);
		assertThatCode(() -> userSignUpStrategy.validatePhoneNumber(phoneNumber)).doesNotThrowAnyException();
	}

	@DisplayName("휴대폰 번호가 존재하면 예외가 발생한다.")
	@Test
	void existsPhoneNumber() {
		PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
		when(userExistsPort.exists(any(PhoneNumber.class))).thenReturn(true);

		assertThatThrownBy(() -> userSignUpStrategy.validatePhoneNumber(phoneNumber))
			.isInstanceOf(IllegalStateException.class);
	}

	@DisplayName("인증 시도 횟수가 5미만이라면 True를 반환한다.")
	@ValueSource(ints = {0, 1, 2, 3, 4})
	@ParameterizedTest(name = "count: {0}")
	void isNotExceed(int count) {
		AuthenticationAttemptCount authenticationAttemptCount =
			new AuthenticationAttemptCountBuilder().setCount(count).build();
		assertThat(userSignUpStrategy.isNotExceed(authenticationAttemptCount)).isTrue();
	}

	@DisplayName("인증 시도 횟수가 5이상이라면 False를 반환한다.")
	@ValueSource(ints = {5, 6, 7, 8, 9, 10})
	@ParameterizedTest(name = "count: {0}")
	void isExceed(int count) {
		AuthenticationAttemptCount authenticationAttemptCount =
			new AuthenticationAttemptCountBuilder().setCount(count).build();
		assertThat(userSignUpStrategy.isNotExceed(authenticationAttemptCount)).isFalse();
	}
}
