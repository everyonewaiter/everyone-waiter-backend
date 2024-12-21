package com.everyonewaiter.message.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.everyonewaiter.PersistenceAdapterTest;
import com.everyonewaiter.fixture.message.MessageHistoryBuilder;
import com.everyonewaiter.message.application.domain.model.MessageHistory;

@PersistenceAdapterTest
@Import(MessageHistoryCreatePersistenceAdapter.class)
class MessageHistoryCreatePersistenceAdapterTest {

	@Autowired
	MessageHistoryCreatePersistenceAdapter messageHistoryCreatePersistenceAdapter;

	@DisplayName("메시지 히스토리를 생성한다.")
	@Test
	void create() {
		List<MessageHistory> messageHistories = List.of(
			new MessageHistoryBuilder().build(),
			new MessageHistoryBuilder().build()
		);
		List<MessageHistory> actual = messageHistoryCreatePersistenceAdapter.create(messageHistories);
		assertThat(actual).hasSize(2);
	}
}
