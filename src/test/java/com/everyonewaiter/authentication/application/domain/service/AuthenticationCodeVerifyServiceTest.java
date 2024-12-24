package com.everyonewaiter.authentication.application.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeVerifyCommand;
import com.everyonewaiter.authentication.application.port.out.AuthenticationCodeFindPort;
import com.everyonewaiter.fixture.authentication.AuthenticationCodeBuilder;
import com.everyonewaiter.fixture.authentication.AuthenticationCodeVerifyCommandBuilder;

@ExtendWith(MockitoExtension.class)
class AuthenticationCodeVerifyServiceTest {

	@Mock
	AuthenticationCodeFindPort authenticationCodeFindPort;

	@InjectMocks
	AuthenticationCodeVerifyService authenticationCodeVerifyService;

	@DisplayName("요청 인증번호와 DB 인증번호가 같다면 검증에 성공한다.")
	@Test
	void verify() {
		AuthenticationCode authenticationCode = new AuthenticationCodeBuilder().build();
		AuthenticationCodeVerifyCommand command = new AuthenticationCodeVerifyCommandBuilder().build();
		when(authenticationCodeFindPort.findOrElseThrowAndDelete(any())).thenReturn(authenticationCode);
		assertThatCode(() -> authenticationCodeVerifyService.verify(command)).doesNotThrowAnyException();
	}

	@DisplayName("요청 인증번호와 DB 인증번호가 다르다면 검증에 실패한다.")
	@Test
	void failVerify() {
		AuthenticationCode authenticationCode = new AuthenticationCodeBuilder().setCode(654321).build();
		AuthenticationCodeVerifyCommand command = new AuthenticationCodeVerifyCommandBuilder().build();
		when(authenticationCodeFindPort.findOrElseThrowAndDelete(any())).thenReturn(authenticationCode);
		assertThatThrownBy(() -> authenticationCodeVerifyService.verify(command))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
