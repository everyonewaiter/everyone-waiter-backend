package com.everyonewaiter.security;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.fixture.user.RawPasswordBuilder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.RawPassword;

@ExtendWith(MockitoExtension.class)
class NativeUserSignInHandlerTest {

	@Mock
	AuthenticationManagerBuilder authenticationManagerBuilder;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	JwtProvider jwtProvider;

	@InjectMocks
	NativeUserSignInHandler userSignInHandler;

	@DisplayName("로그인에 성공하면 액세스 토큰을 발급한다.")
	@Test
	void success() {
		String accessToken = "ACCESS_TOKEN";
		Email email = new EmailBuilder().build();
		RawPassword password = new RawPasswordBuilder().build();

		when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
		when(authenticationManager.authenticate(any()))
			.thenReturn(new TestingAuthenticationToken(email.value(), password.value()));
		when(jwtProvider.generate(any())).thenReturn(accessToken);

		String actual = userSignInHandler.signIn(email, password);

		assertThat(actual).isEqualTo(accessToken);
	}
}
