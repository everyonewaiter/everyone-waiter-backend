package com.everyonewaiter.mail.application.domain.service;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.everyonewaiter.mail.application.domain.model.MailHistory;
import com.everyonewaiter.mail.application.port.in.EmailSendExecutor;
import com.everyonewaiter.mail.application.port.in.command.EmailSendDetail;
import com.everyonewaiter.mail.application.port.out.MailHistoryCreatePort;
import com.everyonewaiter.mail.application.port.out.MailSender;
import com.everyonewaiter.user.application.domain.model.Email;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
class EmailSendExecutorImpl implements EmailSendExecutor {

	private final MailHistoryCreatePort mailHistoryCreatePort;
	private final MailSender mailSender;

	@Override
	public void sendTo(EmailSendDetail detail) {
		MailHistory mailHistory = execute(actionSendTo(), detail);
		mailHistoryCreatePort.create(mailHistory);
	}

	private MailHistory execute(Consumer<EmailSendDetail> action, EmailSendDetail detail) {
		Email sender = detail.sender();
		List<Email> recipients = detail.recipients();
		String subject = detail.subject();
		String content = detail.content();

		try {
			action.accept(detail);
			return MailHistory.success(sender, recipients, subject, content);
		} catch (Exception exception) {
			return MailHistory.fail(sender, recipients, subject, content, exception.getMessage());
		}
	}

	private Consumer<EmailSendDetail> actionSendTo() {
		return detail -> {
			String recipient = String.valueOf(detail.recipients().getFirst());
			mailSender.sendTo(recipient, detail.subject(), detail.content());
		};
	}
}
