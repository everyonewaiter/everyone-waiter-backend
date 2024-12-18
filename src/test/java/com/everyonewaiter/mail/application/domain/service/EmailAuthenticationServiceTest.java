package com.everyonewaiter.mail.application.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import com.everyonewaiter.fixture.mail.EmailAuthenticationCommandBuilder;
import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.mail.application.port.in.EmailSendExecutor;
import com.everyonewaiter.mail.application.port.in.command.EmailAuthenticationCommand;
import com.everyonewaiter.security.ClientOriginRegistry;
import com.everyonewaiter.security.JwtProvider;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserStatus;
import com.everyonewaiter.user.application.port.out.UserFindPort;

@ExtendWith(MockitoExtension.class)
class EmailAuthenticationServiceTest {

	@Mock
	ClientOriginRegistry clientOriginRegistry;

	@Mock
	EmailSendExecutor emailSendExecutor;

	@Mock
	ISpringTemplateEngine templateEngine;

	@Mock
	JwtProvider jwtProvider;

	@Mock
	UserFindPort userFindPort;

	@InjectMocks
	EmailAuthenticationService emailAuthenticationService;

	@DisplayName("이메일 인증 안내 메일을 발송한다.")
	@Test
	void sendAuthenticationMail() {
		User user = new UserBuilder().setStatus(UserStatus.INACTIVE).build();
		EmailAuthenticationCommand command = new EmailAuthenticationCommandBuilder().build();

		when(userFindPort.findOrElseThrow(any())).thenReturn(user);
		when(clientOriginRegistry.getBaseUrl()).thenReturn("https://example.com");
		when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("CONTENT");
		when(jwtProvider.generate(any(), any())).thenReturn("ACCESS_TOKEN");

		emailAuthenticationService.sendAuthenticationMail(command);

		verify(emailSendExecutor, times(1)).sendTo(any());
	}

	@DisplayName("사용자의 상태가 이미 활성화 상태라면 예외가 발생한다.")
	@Test
	void alreadyActivatedUser() {
		User user = new UserBuilder().build();
		EmailAuthenticationCommand command = new EmailAuthenticationCommandBuilder().build();

		when(userFindPort.findOrElseThrow(any())).thenReturn(user);

		assertThatThrownBy(() -> emailAuthenticationService.sendAuthenticationMail(command))
			.isInstanceOf(IllegalStateException.class);
	}
}
