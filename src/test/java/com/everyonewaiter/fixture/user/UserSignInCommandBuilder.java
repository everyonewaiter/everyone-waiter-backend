package com.everyonewaiter.fixture.user;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.RawPassword;
import com.everyonewaiter.user.application.port.in.command.UserSignInCommand;

public class UserSignInCommandBuilder {

	private final Email email = new EmailBuilder().build();
	private final RawPassword password = new RawPasswordBuilder().build();

	public UserSignInCommand build() {
		return new UserSignInCommand(email, password);
	}
}
