package com.everyonewaiter.fixture.user;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.port.in.command.UserActivateCommand;

public class UserActivateCommandBuilder {

	private final Email email = new EmailBuilder().build();

	public UserActivateCommand build() {
		return new UserActivateCommand(email);
	}
}
