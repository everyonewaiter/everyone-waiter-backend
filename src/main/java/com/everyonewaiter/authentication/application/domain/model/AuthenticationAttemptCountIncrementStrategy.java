package com.everyonewaiter.authentication.application.domain.model;

import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public interface AuthenticationAttemptCountIncrementStrategy {

	AuthenticationPurpose getPurpose();

	void validatePhoneNumber(PhoneNumber phoneNumber);

	boolean isNotExceed(AuthenticationAttemptCount authenticationAttemptCount);
}
