package com.everyonewaiter.message.application.domain.model;

import static com.everyonewaiter.message.application.domain.model.MessageHistoryStatus.*;

import java.time.LocalDateTime;

import com.everyonewaiter.common.AggregateRoot;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class MessageHistory extends AggregateRoot {

	private final MessageHistoryId id;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private final Email sender;
	private final PhoneNumber recipient;
	private final String templateCode;
	private final String content;
	private final MessageHistoryStatus status;
	private final String cause;

	public static MessageHistory success(Email sender, PhoneNumber recipient, String templateCode, String content) {
		return of(sender, recipient, templateCode, content, SUCCESS, "");
	}

	public static MessageHistory fail(
		Email sender,
		PhoneNumber recipient,
		String content,
		String templateCode,
		String cause
	) {
		return of(sender, recipient, templateCode, content, FAIL, cause);
	}

	private static MessageHistory of(
		Email sender,
		PhoneNumber recipient,
		String templateCode,
		String content,
		MessageHistoryStatus status,
		String cause
	) {
		MessageHistoryId messageId = new MessageHistoryId();
		LocalDateTime now = LocalDateTime.now();
		return new MessageHistory(messageId, now, now, sender, recipient, templateCode, content, status, cause);
	}
}
