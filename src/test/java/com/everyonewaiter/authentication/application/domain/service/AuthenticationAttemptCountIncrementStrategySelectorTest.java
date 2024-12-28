package com.everyonewaiter.authentication.application.domain.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountIncrementStrategy;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.user.application.port.out.UserExistsPort;

@ExtendWith(MockitoExtension.class)
class AuthenticationAttemptCountIncrementStrategySelectorTest {

	@Mock
	UserExistsPort userExistsPort;

	UserSignUpStrategy userSignUpStrategy = new UserSignUpStrategy(userExistsPort);

	DeviceAuthenticationStrategy deviceAuthenticationStrategy = new DeviceAuthenticationStrategy(userExistsPort);

	AuthenticationAttemptCountIncrementStrategySelector authenticationAttemptCountIncrementStrategySelector;

	@BeforeEach
	void setUp() {
		List<AuthenticationAttemptCountIncrementStrategy> strategies =
			List.of(userSignUpStrategy, deviceAuthenticationStrategy);
		authenticationAttemptCountIncrementStrategySelector =
			new AuthenticationAttemptCountIncrementStrategySelector(strategies);
	}

	@DisplayName("인증 목적으로 인증 시도 횟수 증가 전략을 선택한다.")
	@Test
	void getStrategy() {
		List.of(
			Pair.of(AuthenticationPurpose.USER_SIGN_UP, userSignUpStrategy.getClass()),
			Pair.of(AuthenticationPurpose.DEVICE_AUTHENTICATION, deviceAuthenticationStrategy.getClass())
		).forEach(pair -> {
			AuthenticationAttemptCountIncrementStrategy strategy =
				authenticationAttemptCountIncrementStrategySelector.getStrategy(pair.getFirst());
			assertThat(strategy).isInstanceOf(pair.getSecond());
		});
	}
}
