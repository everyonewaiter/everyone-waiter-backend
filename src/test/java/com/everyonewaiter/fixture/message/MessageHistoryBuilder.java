package com.everyonewaiter.fixture.message;

import java.time.LocalDateTime;

import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.message.application.domain.model.MessageHistory;
import com.everyonewaiter.message.application.domain.model.MessageHistoryId;
import com.everyonewaiter.message.application.domain.model.MessageHistoryStatus;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public class MessageHistoryBuilder {

	private final MessageHistoryId id = new MessageHistoryId();
	private final LocalDateTime createdAt = LocalDateTime.now();
	private final LocalDateTime updatedAt = LocalDateTime.now();
	private final Email sender = new EmailBuilder().build();
	private final PhoneNumber recipient = new PhoneNumberBuilder().build();
	private final MessageHistoryStatus status = MessageHistoryStatus.SUCCESS;

	public MessageHistory build() {
		String templateCode = "TEMPLATE_CODE";
		String content = "CONTENT";
		String cause = "";
		return new MessageHistory(id, createdAt, updatedAt, sender, recipient, templateCode, content, status, cause);
	}
}
