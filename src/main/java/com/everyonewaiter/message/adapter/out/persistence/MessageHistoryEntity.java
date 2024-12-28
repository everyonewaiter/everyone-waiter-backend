package com.everyonewaiter.message.adapter.out.persistence;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Table;

import com.everyonewaiter.common.BaseRootEntity;
import com.everyonewaiter.message.application.domain.model.MessageHistoryStatus;

import lombok.Getter;

@Getter
@Table(name = "message_history")
class MessageHistoryEntity extends BaseRootEntity {

	private final String sender;
	private final String recipient;
	private final String templateCode;
	private final String content;
	private final MessageHistoryStatus status;
	private final String cause;

	public MessageHistoryEntity(
		Long id,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		String sender,
		String recipient,
		String templateCode,
		String content,
		MessageHistoryStatus status,
		String cause
	) {
		super(id, createdAt, updatedAt);
		this.sender = sender;
		this.recipient = recipient;
		this.templateCode = templateCode;
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
