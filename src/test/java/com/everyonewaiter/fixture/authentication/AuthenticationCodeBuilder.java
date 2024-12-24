package com.everyonewaiter.fixture.authentication;

import java.time.Duration;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public class AuthenticationCodeBuilder {

	private final PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
	private final AuthenticationPurpose purpose = AuthenticationPurpose.USER_SIGN_UP;
	private final Long expirationSeconds = Duration.ofMinutes(5L).getSeconds();

	public AuthenticationCode build() {
		return new AuthenticationCode(phoneNumber, purpose, 123456, expirationSeconds);
	}
}
