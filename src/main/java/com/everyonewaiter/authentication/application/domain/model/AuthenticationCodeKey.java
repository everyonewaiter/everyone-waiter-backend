package com.everyonewaiter.authentication.application.domain.model;

import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public record AuthenticationCodeKey(
	PhoneNumber phoneNumber,
	AuthenticationPurpose purpose
) implements AuthenticationKey {

	public String getValue() {
		return KEY_FORMAT.formatted("authentication_code", purpose.name().toLowerCase(), phoneNumber.value());
	}

	@Override
	public String toString() {
		return getValue();
	}
}
