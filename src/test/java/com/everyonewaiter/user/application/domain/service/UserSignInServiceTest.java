package com.everyonewaiter.user.application.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everyonewaiter.fixture.user.UserSignInCommandBuilder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.RawPassword;
import com.everyonewaiter.user.application.port.in.command.UserSignInCommand;
import com.everyonewaiter.user.application.port.in.response.JwtResponse;
import com.everyonewaiter.user.application.port.out.UserSignInPort;

@ExtendWith(MockitoExtension.class)
class UserSignInServiceTest {

	@Mock
	UserSignInPort userSignInPort;

	@InjectMocks
	UserSignInService userSignInService;

	@DisplayName("로그인에 성공하면 액세스 토큰을 발급받는다.")
	@Test
	void signIn() {
		String accessToken = "ACCESS_TOKEN";
		UserSignInCommand command = new UserSignInCommandBuilder().build();

		when(userSignInPort.signIn(any(Email.class), any(RawPassword.class))).thenReturn(accessToken);

		JwtResponse actual = userSignInService.signIn(command);

		assertThat(actual.accessToken()).isEqualTo(accessToken);
	}
}
