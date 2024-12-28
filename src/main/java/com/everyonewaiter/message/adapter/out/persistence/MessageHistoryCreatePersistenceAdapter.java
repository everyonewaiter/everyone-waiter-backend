package com.everyonewaiter.message.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import com.everyonewaiter.message.application.domain.model.MessageHistory;
import com.everyonewaiter.message.application.port.out.MessageHistoryCreatePort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class MessageHistoryCreatePersistenceAdapter implements MessageHistoryCreatePort {

	private final JdbcAggregateTemplate jdbcAggregateTemplate;
	private final MessageHistoryMapper messageHistoryMapper;

	@Override
	public List<MessageHistory> create(List<MessageHistory> messageHistories) {
		List<MessageHistoryEntity> entities = messageHistories.stream().map(messageHistoryMapper::mapToEntity).toList();
		return jdbcAggregateTemplate.insertAll(entities).stream().map(messageHistoryMapper::mapToDomain).toList();
	}
}
