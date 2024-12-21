package com.everyonewaiter.message.adapter.out.persistence;

import com.everyonewaiter.common.PersistenceMapper;
import com.everyonewaiter.message.application.domain.model.MessageHistory;
import com.everyonewaiter.message.application.domain.model.MessageHistoryId;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

@PersistenceMapper
class MessageHistoryMapper {

	MessageHistory mapToDomain(MessageHistoryEntity messageHistoryEntity) {
		return new MessageHistory(
			new MessageHistoryId(messageHistoryEntity.getId()),
			messageHistoryEntity.getCreatedAt(),
			messageHistoryEntity.getUpdatedAt(),
			new Email(messageHistoryEntity.getSender()),
			new PhoneNumber(messageHistoryEntity.getRecipient()),
			messageHistoryEntity.getTemplateCode(),
			messageHistoryEntity.getContent(),
			messageHistoryEntity.getStatus(),
			messageHistoryEntity.getCause()
		);
	}

	MessageHistoryEntity mapToEntity(MessageHistory messageHistory) {
		return new MessageHistoryEntity(
			messageHistory.getId().value(),
			messageHistory.getCreatedAt(),
			messageHistory.getUpdatedAt(),
			messageHistory.getSender().value(),
			messageHistory.getRecipient().value(),
			messageHistory.getTemplateCode(),
			messageHistory.getContent(),
			messageHistory.getStatus(),
			messageHistory.getCause()
		);
	}
}
