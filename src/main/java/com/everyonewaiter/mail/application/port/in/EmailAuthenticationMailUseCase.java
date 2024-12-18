package com.everyonewaiter.mail.application.port.in;

import com.everyonewaiter.mail.application.port.in.command.EmailAuthenticationMailCommand;

public interface EmailAuthenticationMailUseCase {

	void sendEmailAuthenticationMail(EmailAuthenticationMailCommand command);
}
