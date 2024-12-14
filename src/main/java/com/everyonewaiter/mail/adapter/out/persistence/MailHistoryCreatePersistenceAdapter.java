package com.everyonewaiter.mail.adapter.out.persistence;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import com.everyonewaiter.mail.application.domain.model.MailHistory;
import com.everyonewaiter.mail.application.port.out.MailHistoryCreatePort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class MailHistoryCreatePersistenceAdapter implements MailHistoryCreatePort {

	private final JdbcAggregateTemplate jdbcAggregateTemplate;
	private final MailHistoryMapper mailHistoryMapper;

	@Override
	public MailHistory create(MailHistory mailHistory) {
		MailHistoryEntity mailHistoryEntity = jdbcAggregateTemplate.insert(mailHistoryMapper.mapToEntity(mailHistory));
		return mailHistoryMapper.mapToDomain(mailHistoryEntity);
	}
}
