package com.everyonewaiter.authentication.application.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationCodeGenerator;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeGenerateCommand;
import com.everyonewaiter.authentication.application.port.out.AuthenticationCodeCreatePort;
import com.everyonewaiter.fixture.authentication.AuthenticationCodeGenerateCommandBuilder;

@ExtendWith(MockitoExtension.class)
class AuthenticationCodeGenerateServiceTest {

	@Mock
	AuthenticationCodeGenerator authenticationCodeGenerator;

	@Mock
	AuthenticationCodeCreatePort authenticationCodeCreatePort;

	@InjectMocks
	AuthenticationCodeGenerateService authenticationCodeGenerateService;

	@DisplayName("인증 번호를 생성한다.")
	@Test
	void generate() {
		Integer code = 123456;
		AuthenticationCodeGenerateCommand command = new AuthenticationCodeGenerateCommandBuilder().build();
		ArgumentCaptor<AuthenticationCode> captor = ArgumentCaptor.forClass(AuthenticationCode.class);
		when(authenticationCodeGenerator.generate()).thenReturn(code);

		authenticationCodeGenerateService.generate(command);

		verify(authenticationCodeCreatePort, times(1)).create(captor.capture());
		assertThat(captor.getValue().getCode()).isEqualTo(code);
	}
}
