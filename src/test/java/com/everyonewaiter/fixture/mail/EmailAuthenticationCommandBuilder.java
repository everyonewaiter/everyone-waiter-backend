package com.everyonewaiter.fixture.mail;

import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.mail.application.port.in.command.EmailAuthenticationCommand;
import com.everyonewaiter.user.application.domain.model.Email;

public class EmailAuthenticationCommandBuilder {

	private final Email email = new EmailBuilder().build();

	public EmailAuthenticationCommand build() {
		return new EmailAuthenticationCommand(email);
	}
}
