package com.everyonewaiter.fixture.mail;

import java.time.LocalDateTime;
import java.util.List;

import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.mail.application.domain.model.MailHistory;
import com.everyonewaiter.mail.application.domain.model.MailHistoryId;
import com.everyonewaiter.mail.application.domain.model.MailHistoryStatus;
import com.everyonewaiter.user.application.domain.model.Email;

public class MailHistoryBuilder {

	private final MailHistoryId id = new MailHistoryId();
	private final LocalDateTime createdAt = LocalDateTime.now();
	private final LocalDateTime updatedAt = LocalDateTime.now();
	private final Email sender = new EmailBuilder().build();
	private final List<Email> recipients = List.of(
		new EmailBuilder().setEmail("handwoong@naver.com").build(),
		new EmailBuilder().setEmail("handwoong@daum.net").build()
	);
	private final MailHistoryStatus status = MailHistoryStatus.SUCCESS;

	public MailHistory build() {
		String subject = "SUBJECT";
		String content = "CONTENT";
		String cause = "";
		return new MailHistory(id, createdAt, updatedAt, sender, recipients, subject, content, status, cause);
	}
}
