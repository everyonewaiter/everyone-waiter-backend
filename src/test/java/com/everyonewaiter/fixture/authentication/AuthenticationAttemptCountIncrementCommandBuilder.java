package com.everyonewaiter.fixture.authentication;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationAttemptCountIncrementCommand;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public class AuthenticationAttemptCountIncrementCommandBuilder {

	private final PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
	private final AuthenticationPurpose purpose = AuthenticationPurpose.USER_SIGN_UP;

	public AuthenticationAttemptCountIncrementCommand build() {
		return new AuthenticationAttemptCountIncrementCommand(phoneNumber, purpose);
	}
}
