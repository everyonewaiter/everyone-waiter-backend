package com.everyonewaiter.mail.application.port.in;

import com.everyonewaiter.mail.application.port.in.command.EmailAuthenticationCommand;

public interface EmailAuthenticationUseCase {

	void sendAuthenticationMail(EmailAuthenticationCommand command);
}
