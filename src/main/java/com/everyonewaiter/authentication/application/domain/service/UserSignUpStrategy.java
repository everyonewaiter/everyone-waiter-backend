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
class UserSignUpStrategy implements AuthenticationAttemptCountIncrementStrategy {

	private final UserExistsPort userExistsPort;

	@Override
	public AuthenticationPurpose getPurpose() {
		return AuthenticationPurpose.USER_SIGN_UP;
	}

	@Override
	public void validatePhoneNumber(PhoneNumber phoneNumber) {
		check(!userExistsPort.exists(phoneNumber), () -> format("already.use.phone-number", phoneNumber));
	}

	@Override
	public boolean isNotExceed(AuthenticationAttemptCount authenticationAttemptCount) {
		return authenticationAttemptCount.getCount() < 5;
	}
}
