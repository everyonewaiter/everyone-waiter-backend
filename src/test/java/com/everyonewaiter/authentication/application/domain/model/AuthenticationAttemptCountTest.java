package com.everyonewaiter.authentication.application.domain.model;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import com.everyonewaiter.fixture.authentication.AuthenticationAttemptCountBuilder;

@ExtendWith(MockitoExtension.class)
class AuthenticationAttemptCountTest {

	@Mock
	AuthenticationAttemptCountIncrementStrategy authenticationAttemptCountIncrementStrategy;

	@DisplayName("인증 시도 횟수가 1이하라면 새로운 인증 시도 횟수이다.")
	@Test
	void isNew() {
		List.of(
			Pair.of(new AuthenticationAttemptCountBuilder().build(), true),
			Pair.of(new AuthenticationAttemptCountBuilder().setCount(2).build(), false)
		).forEach(pair -> assertThat(pair.getFirst().isNew()).isEqualTo(pair.getSecond()));
	}

	@DisplayName("일일 인증 시도 횟수가 초과하지 않았다면 인증 시도 횟수를 증가시킨다.")
	@Test
	void increment() {
		AuthenticationAttemptCount authenticationAttemptCount = new AuthenticationAttemptCountBuilder().build();
		when(authenticationAttemptCountIncrementStrategy.isNotExceed(any())).thenReturn(true);

		authenticationAttemptCount.increment(authenticationAttemptCountIncrementStrategy);

		assertThat(authenticationAttemptCount.getCount()).isEqualTo(1);
	}

	@DisplayName("일일 인증 시도 횟수가 초과했다면 인증 시도 횟수를 증가 시 예외가 발생한다.")
	@Test
	void exceedIncrement() {
		AuthenticationAttemptCount authenticationAttemptCount = new AuthenticationAttemptCountBuilder().build();
		when(authenticationAttemptCountIncrementStrategy.isNotExceed(any())).thenReturn(false);
		assertThatThrownBy(() -> authenticationAttemptCount.increment(authenticationAttemptCountIncrementStrategy))
			.isInstanceOf(IllegalStateException.class);
	}
}
