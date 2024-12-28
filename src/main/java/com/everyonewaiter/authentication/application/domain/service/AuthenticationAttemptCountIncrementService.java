package com.everyonewaiter.authentication.application.domain.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCount;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountIncrementStrategy;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountKey;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.authentication.application.port.in.AuthenticationAttemptCountIncrementUseCase;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationAttemptCountIncrementCommand;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountCreatePort;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountFindPort;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountIncrementPort;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthenticationAttemptCountIncrementService implements AuthenticationAttemptCountIncrementUseCase {

	private static final Long ONE_DAY_SECONDS = Duration.ofDays(1).getSeconds();

	private final AuthenticationAttemptCountCreatePort authenticationAttemptCountCreatePort;
	private final AuthenticationAttemptCountFindPort authenticationAttemptCountFindPort;
	private final AuthenticationAttemptCountIncrementPort authenticationAttemptCountIncrementPort;
	private final AuthenticationAttemptCountIncrementStrategySelector strategySelector;

	@Override
	public void increment(AuthenticationAttemptCountIncrementCommand command) {
		PhoneNumber phoneNumber = command.phoneNumber();
		AuthenticationPurpose purpose = command.purpose();

		AuthenticationAttemptCountIncrementStrategy strategy = strategySelector.getStrategy(purpose);
		strategy.validatePhoneNumber(phoneNumber);

		AuthenticationAttemptCountKey key = new AuthenticationAttemptCountKey(phoneNumber, purpose);
		AuthenticationAttemptCount authenticationAttemptCount = authenticationAttemptCountFindPort.find(key)
			.orElseGet(() -> new AuthenticationAttemptCount(phoneNumber, purpose, ONE_DAY_SECONDS));

		authenticationAttemptCount.increment(strategy);
		incrementAuthenticationAttemptCount(key, authenticationAttemptCount);
	}

	private void incrementAuthenticationAttemptCount(
		AuthenticationAttemptCountKey key,
		AuthenticationAttemptCount authenticationAttemptCount
	) {
		if (authenticationAttemptCount.isNew()) {
			authenticationAttemptCountCreatePort.create(authenticationAttemptCount);
		} else {
			authenticationAttemptCountIncrementPort.increment(key);
		}
	}
}
