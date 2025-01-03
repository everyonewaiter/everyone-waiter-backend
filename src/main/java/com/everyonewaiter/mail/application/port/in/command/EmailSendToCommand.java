package com.everyonewaiter.mail.application.port.in.command;

import java.util.List;

import com.everyonewaiter.user.application.domain.model.Email;

public record EmailSendToCommand(
	Email sender,
	Email recipient,
	String subject,
	String content
) implements EmailSendDetail {

	public EmailSendToCommand(Email recipient, String subject, String content) {
		this(new Email(DEFAULT_SENDER_MAIL), recipient, subject, content);
	}

	@Override
	public List<Email> recipients() {
		return List.of(recipient);
	}
}
