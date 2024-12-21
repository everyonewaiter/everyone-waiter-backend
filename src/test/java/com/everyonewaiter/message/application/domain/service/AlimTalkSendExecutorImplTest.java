package com.everyonewaiter.message.application.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.mail.MailProperties;

import com.everyonewaiter.fixture.message.AlimTalkSendDetailBuilder;
import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.message.application.domain.model.MessageHistory;
import com.everyonewaiter.message.application.domain.model.MessageHistoryStatus;
import com.everyonewaiter.message.application.port.in.command.AlimTalkSendDetail;
import com.everyonewaiter.message.application.port.out.AlimTalkSender;
import com.everyonewaiter.message.application.port.out.MessageHistoryCreatePort;

@ExtendWith(MockitoExtension.class)
class AlimTalkSendExecutorImplTest {

	@Mock
	AlimTalkSender alimTalkSender;

	@Mock
	MailProperties mailProperties;

	@Mock
	MessageHistoryCreatePort messageHistoryCreatePort;

	@InjectMocks
	AlimTalkSendExecutorImpl alimTalkSendExecutor;

	@Captor
	ArgumentCaptor<List<MessageHistory>> captor;

	@BeforeEach
	void setUp() {
		when(mailProperties.getUsername()).thenReturn(new EmailBuilder().build().value());
	}

	@DisplayName("알림톡 발송에 성공하면 메시지 히스토리의 상태는 SUCCESS이다.")
	@Test
	void successSendTo() {
		AlimTalkSendDetail alimTalkSendDetail = new AlimTalkSendDetailBuilder().build();

		doNothing().when(alimTalkSender).sendTo(any(), any());

		alimTalkSendExecutor.sendTo(alimTalkSendDetail);

		verify(messageHistoryCreatePort, times(1)).create(captor.capture());
		assertThat(captor.getValue().getFirst().getStatus()).isEqualTo(MessageHistoryStatus.SUCCESS);
	}

	@DisplayName("알림톡 발송에 실패하면 메시지 히스토리의 상태는 FAIL이다.")
	@Test
	void failSendTo() {
		AlimTalkSendDetail alimTalkSendDetail = new AlimTalkSendDetailBuilder().build();

		doThrow(new RuntimeException()).when(alimTalkSender).sendTo(any(), any());

		alimTalkSendExecutor.sendTo(alimTalkSendDetail);

		verify(messageHistoryCreatePort, times(1)).create(captor.capture());
		assertThat(captor.getValue().getFirst().getStatus()).isEqualTo(MessageHistoryStatus.FAIL);
	}
}
