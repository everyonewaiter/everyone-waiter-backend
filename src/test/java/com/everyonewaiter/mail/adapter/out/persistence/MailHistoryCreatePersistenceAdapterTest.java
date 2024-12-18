package com.everyonewaiter.mail.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.everyonewaiter.PersistenceAdapterTest;
import com.everyonewaiter.fixture.mail.MailHistoryBuilder;
import com.everyonewaiter.mail.application.domain.model.MailHistory;

@PersistenceAdapterTest
@Import(MailHistoryCreatePersistenceAdapter.class)
class MailHistoryCreatePersistenceAdapterTest {

	@Autowired
	MailHistoryCreatePersistenceAdapter mailHistoryCreatePersistenceAdapter;

	@DisplayName("메일 히스토리를 생성한다.")
	@Test
	void create() {
		MailHistory mailHistory = new MailHistoryBuilder().build();
		MailHistory actual = mailHistoryCreatePersistenceAdapter.create(mailHistory);
		assertThat(actual).isEqualTo(mailHistory);
	}
}
