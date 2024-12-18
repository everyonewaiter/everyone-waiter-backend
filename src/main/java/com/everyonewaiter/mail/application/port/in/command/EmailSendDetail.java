package com.everyonewaiter.mail.application.port.in.command;

import java.util.List;

import com.everyonewaiter.user.application.domain.model.Email;

public interface EmailSendDetail {

	List<Email> recipients();

	String subject();

	String content();
}
