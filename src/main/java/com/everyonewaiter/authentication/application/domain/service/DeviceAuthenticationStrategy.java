package com.everyonewaiter.authentication.application.domain.service;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;
import static com.everyonewaiter.common.PreconditionChecker.*;

import org.springframework.stereotype.Component;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCount;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountIncrementStrategy;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.port.out.UserExistsPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class DeviceAuthenticationStrategy implements AuthenticationAttemptCountIncrementStrategy {

	private final UserExistsPort userExistsPort;

	@Override
	public AuthenticationPurpose getPurpose() {
		return AuthenticationPurpose.DEVICE_AUTHENTICATION;
	}

	@Override
	public void validatePhoneNumber(PhoneNumber phoneNumber) {
		check(userExistsPort.exists(phoneNumber), () -> format("require.exist.phone-number", phoneNumber));
	}

	@Override
	public boolean isNotExceed(AuthenticationAttemptCount authenticationAttemptCount) {
		return true;
	}
}
