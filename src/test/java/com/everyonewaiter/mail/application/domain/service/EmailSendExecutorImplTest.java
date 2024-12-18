package com.everyonewaiter.mail.application.domain.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.mail.MailProperties;

import com.everyonewaiter.fixture.mail.EmailSendDetailBuilder;
import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.mail.application.domain.model.MailHistory;
import com.everyonewaiter.mail.application.domain.model.MailHistoryStatus;
import com.everyonewaiter.mail.application.port.in.command.EmailSendDetail;
import com.everyonewaiter.mail.application.port.out.MailHistoryCreatePort;
import com.everyonewaiter.mail.application.port.out.MailSender;

@ExtendWith(MockitoExtension.class)
class EmailSendExecutorImplTest {

	@Mock
	MailHistoryCreatePort mailHistoryCreatePort;

	@Mock
	MailProperties mailProperties;

	@Mock
	MailSender mailSender;

	@InjectMocks
	EmailSendExecutorImpl emailSendExecutor;

	@BeforeEach
	void setUp() {
		when(mailProperties.getUsername()).thenReturn(new EmailBuilder().build().value());
	}

	@DisplayName("메일 발송에 성공하면 메일 히스토리의 상태는 SUCCESS이다.")
	@Test
	void successSendTo() {
		EmailSendDetail emailSendDetail = new EmailSendDetailBuilder().build();
		ArgumentCaptor<MailHistory> captor = ArgumentCaptor.forClass(MailHistory.class);

		doNothing().when(mailSender).sendTo(any(), any(), any());

		emailSendExecutor.sendTo(emailSendDetail);

		verify(mailHistoryCreatePort, times(1)).create(captor.capture());
		assertThat(captor.getValue().getStatus()).isEqualTo(MailHistoryStatus.SUCCESS);
	}

	@DisplayName("메일 발송에 실패하면 메일 히스토리의 상태는 FAIL이다.")
	@Test
	void failSendTo() {
		EmailSendDetail emailSendDetail = new EmailSendDetailBuilder().build();
		ArgumentCaptor<MailHistory> captor = ArgumentCaptor.forClass(MailHistory.class);

		doThrow(new RuntimeException()).when(mailSender).sendTo(any(), any(), any());

		emailSendExecutor.sendTo(emailSendDetail);

		verify(mailHistoryCreatePort, times(1)).create(captor.capture());
		assertThat(captor.getValue().getStatus()).isEqualTo(MailHistoryStatus.FAIL);
	}
}
