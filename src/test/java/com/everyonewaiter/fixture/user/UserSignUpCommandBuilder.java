package com.everyonewaiter.fixture.user;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.domain.model.RawPassword;
import com.everyonewaiter.user.application.port.in.command.UserSignUpCommand;

public class UserSignUpCommandBuilder {

	private final Email email = new EmailBuilder().build();
	private final RawPassword password = new RawPasswordBuilder().build();
	private final PhoneNumber phoneNumber = new PhoneNumberBuilder().build();

	public UserSignUpCommand build() {
		return new UserSignUpCommand(email, password, phoneNumber);
	}
}
