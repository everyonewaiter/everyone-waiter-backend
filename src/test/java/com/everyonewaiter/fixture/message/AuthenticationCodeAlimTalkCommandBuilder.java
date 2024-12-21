package com.everyonewaiter.fixture.message;

import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.message.application.port.in.command.AuthenticationCodeAlimTalkCommand;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public class AuthenticationCodeAlimTalkCommandBuilder {

	private final PhoneNumber phoneNumber = new PhoneNumberBuilder().build();

	public AuthenticationCodeAlimTalkCommand build() {
		return new AuthenticationCodeAlimTalkCommand(phoneNumber, 123456);
	}
}
