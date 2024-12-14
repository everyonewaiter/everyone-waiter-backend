package com.everyonewaiter.mail.adapter.out.persistence;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Table;

import com.everyonewaiter.common.BaseRootEntity;
import com.everyonewaiter.mail.application.domain.model.MailHistoryStatus;

import lombok.Getter;

@Getter
@Table(name = "mail_history")
class MailHistoryEntity extends BaseRootEntity {

	private final String sender;
	private final String recipients;
	private final String subject;
	private final String content;
	private final MailHistoryStatus status;
	private final String cause;

	MailHistoryEntity(
		Long id,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		String sender,
		String recipients,
		String subject,
		String content,
		MailHistoryStatus status,
		String cause
	) {
		super(id, createdAt, updatedAt);
		this.sender = sender;
		this.recipients = recipients;
		this.subject = subject;
		this.content = content;
		this.status = status;
		this.cause = cause;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
