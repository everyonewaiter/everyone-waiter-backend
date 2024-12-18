package com.everyonewaiter.fixture.mail;

import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.mail.application.port.in.command.EmailAuthenticationMailCommand;
import com.everyonewaiter.user.application.domain.model.Email;

public class EmailAuthenticationMailCommandBuilder {

	private final Email email = new EmailBuilder().build();

	public EmailAuthenticationMailCommand build() {
		return new EmailAuthenticationMailCommand(email);
	}
}
