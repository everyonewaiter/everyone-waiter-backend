package com.everyonewaiter.message.application.domain.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everyonewaiter.fixture.message.AuthenticationCodeAlimTalkCommandBuilder;
import com.everyonewaiter.message.application.port.in.AlimTalkSendExecutor;
import com.everyonewaiter.message.application.port.in.command.AuthenticationCodeAlimTalkCommand;

@ExtendWith(MockitoExtension.class)
class AuthenticationCodeAlimTalkServiceTest {

	@Mock
	AlimTalkSendExecutor alimTalkSendExecutor;

	@InjectMocks
	AuthenticationCodeAlimTalkService authenticationCodeAlimTalkService;

	@DisplayName("인증 번호 알림톡을 발송한다.")
	@Test
	void sendAuthenticationCodeAlimTalk() {
		AuthenticationCodeAlimTalkCommand command = new AuthenticationCodeAlimTalkCommandBuilder().build();
		authenticationCodeAlimTalkService.sendAuthenticationCodeAlimTalk(command);
		verify(alimTalkSendExecutor, times(1)).sendTo(any());
	}
}
