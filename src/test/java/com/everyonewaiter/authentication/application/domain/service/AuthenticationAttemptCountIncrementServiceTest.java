package com.everyonewaiter.authentication.application.domain.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCount;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountIncrementStrategy;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationAttemptCountIncrementCommand;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountCreatePort;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountFindPort;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountIncrementPort;
import com.everyonewaiter.fixture.authentication.AuthenticationAttemptCountBuilder;
import com.everyonewaiter.fixture.authentication.AuthenticationAttemptCountIncrementCommandBuilder;

@ExtendWith(MockitoExtension.class)
class AuthenticationAttemptCountIncrementServiceTest {

	@Mock
	AuthenticationAttemptCountCreatePort authenticationAttemptCountCreatePort;

	@Mock
	AuthenticationAttemptCountFindPort authenticationAttemptCountFindPort;

	@Mock
	AuthenticationAttemptCountIncrementPort authenticationAttemptCountIncrementPort;

	@Mock
	AuthenticationAttemptCountIncrementStrategy authenticationAttemptCountIncrementStrategy;

	@Mock
	AuthenticationAttemptCountIncrementStrategySelector authenticationAttemptCountIncrementStrategySelector;

	@InjectMocks
	AuthenticationAttemptCountIncrementService authenticationAttemptCountIncrementService;

	@BeforeEach
	void setUp() {
		doNothing().when(authenticationAttemptCountIncrementStrategy).validatePhoneNumber(any());
		doReturn(true).when(authenticationAttemptCountIncrementStrategy).isNotExceed(any());
		doReturn(authenticationAttemptCountIncrementStrategy)
			.when(authenticationAttemptCountIncrementStrategySelector).getStrategy(any());
	}

	@DisplayName("인증 시도 횟수를 생성한다.")
	@Test
	void create() {
		AuthenticationAttemptCountIncrementCommand command =
			new AuthenticationAttemptCountIncrementCommandBuilder().build();

		when(authenticationAttemptCountFindPort.find(any())).thenReturn(Optional.empty());

		authenticationAttemptCountIncrementService.increment(command);

		verify(authenticationAttemptCountCreatePort, times(1)).create(any());
		verify(authenticationAttemptCountIncrementPort, times(0)).increment(any());
	}

	@DisplayName("인증 시도 횟수를 증가시킨다.")
	@Test
	void increment() {
		AuthenticationAttemptCountIncrementCommand command =
			new AuthenticationAttemptCountIncrementCommandBuilder().build();
		AuthenticationAttemptCount authenticationAttemptCount =
			new AuthenticationAttemptCountBuilder().setCount(2).build();

		when(authenticationAttemptCountFindPort.find(any())).thenReturn(Optional.of(authenticationAttemptCount));

		authenticationAttemptCountIncrementService.increment(command);

		verify(authenticationAttemptCountCreatePort, times(0)).create(any());
		verify(authenticationAttemptCountIncrementPort, times(1)).increment(any());
	}
}
