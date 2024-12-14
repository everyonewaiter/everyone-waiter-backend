package com.everyonewaiter.mail.application.port.in.command;

import java.util.List;

import com.everyonewaiter.user.application.domain.model.Email;

public record EmailSendCommand(Email recipient, String subject, String content) implements EmailSendDetail {

	@Override
	public List<Email> recipients() {
		return List.of(recipient);
	}
}
