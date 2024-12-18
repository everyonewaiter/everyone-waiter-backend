package com.everyonewaiter.mail.application.domain.model;

import static com.everyonewaiter.mail.application.domain.model.MailHistoryStatus.*;

import java.time.LocalDateTime;
import java.util.List;

import com.everyonewaiter.common.AggregateRoot;
import com.everyonewaiter.user.application.domain.model.Email;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class MailHistory extends AggregateRoot {

	private final MailHistoryId id;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private final Email sender;
	private final List<Email> recipients;
	private final String subject;
	private final String content;
	private final MailHistoryStatus status;
	private final String cause;

	public static MailHistory success(Email sender, List<Email> recipients, String subject, String content) {
		return of(sender, recipients, subject, content, SUCCESS, "");
	}

	public static MailHistory fail(Email sender, List<Email> recipients, String subject, String content, String cause) {
		return of(sender, recipients, subject, content, FAIL, cause);
	}

	private static MailHistory of(
		Email sender,
		List<Email> recipients,
		String subject,
		String content,
		MailHistoryStatus status,
		String cause
	) {
		LocalDateTime now = LocalDateTime.now();
		return new MailHistory(new MailHistoryId(), now, now, sender, recipients, subject, content, status, cause);
	}
}
