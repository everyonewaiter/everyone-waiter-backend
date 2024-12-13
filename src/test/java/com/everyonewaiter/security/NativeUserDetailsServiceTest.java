package com.everyonewaiter.security;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.port.out.UserFindPort;
import com.everyonewaiter.user.application.port.out.UserStateUpdatePort;

@ExtendWith(MockitoExtension.class)
class NativeUserDetailsServiceTest {

	private static final String EMAIL = "handwoong@gmail.com";

	@Mock
	UserFindPort userFindPort;

	@Mock
	UserStateUpdatePort userStateUpdatePort;

	@InjectMocks
	NativeUserDetailsService nativeUserDetailsService;

	@DisplayName("사용자의 로그인 시간을 갱신한다.")
	@Test
	void renewalLastSignInTime() {
		LocalDateTime currentTime = LocalDateTime.now();
		User user = new UserBuilder().setLastSignInTime(currentTime).build();

		when(userFindPort.find(any(Email.class))).thenReturn(Optional.of(user));
		doNothing().when(userStateUpdatePort).update(any(User.class));

		nativeUserDetailsService.loadUserByUsername(EMAIL);

		assertThat(user.getLastSignInTime()).isAfter(currentTime);
	}

	@DisplayName("이메일로 사용자를 찾지 못하면 예외가 발생한다.")
	@Test
	void notFoundUser() {
		when(userFindPort.find(any(Email.class))).thenReturn(Optional.empty());
		assertThatThrownBy(() -> nativeUserDetailsService.loadUserByUsername(EMAIL))
			.isInstanceOf(UsernameNotFoundException.class);
	}
}
