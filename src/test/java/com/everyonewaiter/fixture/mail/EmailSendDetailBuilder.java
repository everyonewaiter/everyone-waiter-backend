package com.everyonewaiter.fixture.mail;

import java.util.List;

import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.mail.application.port.in.command.EmailSendDetail;
import com.everyonewaiter.user.application.domain.model.Email;

public class EmailSendDetailBuilder {

	private final List<Email> recipients = List.of(new EmailBuilder().build());
	private final String subject = "SUBJECT";
	private final String content = "CONTENT";

	public EmailSendDetail build() {
		return new EmailSendDetail() {

			@Override
			public List<Email> recipients() {
				return recipients;
			}

			@Override
			public String subject() {
				return subject;
			}

			@Override
			public String content() {
				return content;
			}
		};
	}
}
