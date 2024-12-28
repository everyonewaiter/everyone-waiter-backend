package com.everyonewaiter.authentication.application.domain.model;

import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public record AuthenticationAttemptCountKey(
	PhoneNumber phoneNumber,
	AuthenticationPurpose purpose
) implements AuthenticationKey {

	@Override
	public String getValue() {
		return KEY_FORMAT.formatted("authentication_attempt_count", purpose.name().toLowerCase(), phoneNumber.value());
	}

	@Override
	public String toString() {
		return getValue();
	}
}
