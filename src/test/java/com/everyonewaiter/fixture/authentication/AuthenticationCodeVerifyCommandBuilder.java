package com.everyonewaiter.fixture.authentication;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeVerifyCommand;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public class AuthenticationCodeVerifyCommandBuilder {

	private final PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
	private final AuthenticationPurpose purpose = AuthenticationPurpose.USER_SIGN_UP;

	public AuthenticationCodeVerifyCommand build() {
		return new AuthenticationCodeVerifyCommand(phoneNumber, purpose, 123456);
	}
}
