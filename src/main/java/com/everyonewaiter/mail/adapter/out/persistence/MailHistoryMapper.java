package com.everyonewaiter.mail.adapter.out.persistence;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.everyonewaiter.common.PersistenceMapper;
import com.everyonewaiter.mail.application.domain.model.MailHistory;
import com.everyonewaiter.mail.application.domain.model.MailHistoryId;
import com.everyonewaiter.user.application.domain.model.Email;

@PersistenceMapper
class MailHistoryMapper {

	private static final String DELIMITER = ",";

	MailHistory mapToDomain(MailHistoryEntity mailHistoryEntity) {
		return new MailHistory(
			new MailHistoryId(mailHistoryEntity.getId()),
			mailHistoryEntity.getCreatedAt(),
			mailHistoryEntity.getUpdatedAt(),
			new Email(mailHistoryEntity.getSender()),
			Arrays.stream(mailHistoryEntity.getRecipients().split(DELIMITER)).map(Email::new).toList(),
			mailHistoryEntity.getSubject(),
			mailHistoryEntity.getContent(),
			mailHistoryEntity.getStatus(),
			mailHistoryEntity.getCause()
		);
	}

	MailHistoryEntity mapToEntity(MailHistory mailHistory) {
		return new MailHistoryEntity(
			mailHistory.getId().value(),
			mailHistory.getCreatedAt(),
			mailHistory.getUpdatedAt(),
			mailHistory.getSender().value(),
			mailHistory.getRecipients().stream().map(Email::value).collect(Collectors.joining(DELIMITER)),
			mailHistory.getSubject(),
			mailHistory.getContent(),
			mailHistory.getStatus(),
			mailHistory.getCause()
		);
	}
}
