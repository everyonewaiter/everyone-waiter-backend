package com.everyonewaiter.user.application.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.fixture.user.UserSignUpCommandBuilder;
import com.everyonewaiter.security.Encodable;
import com.everyonewaiter.security.RawPasswordEncoder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserId;
import com.everyonewaiter.user.application.port.in.command.UserSignUpCommand;
import com.everyonewaiter.user.application.port.out.UserCreatePort;
import com.everyonewaiter.user.application.port.out.UserExistsPort;

@ExtendWith(MockitoExtension.class)
class UserSignUpServiceTest {

	@Mock
	RawPasswordEncoder rawPasswordEncoder;

	@Mock
	UserCreatePort userCreatePort;

	@Mock
	UserExistsPort userExistsPort;

	@InjectMocks
	UserSignUpService userSignUpService;

	@DisplayName("사용자 가입이 정상적으로 완료된다.")
	@Test
	void signUp() {
		User user = new UserBuilder().build();
		UserSignUpCommand command = new UserSignUpCommandBuilder().build();

		when(rawPasswordEncoder.encode(any(Encodable.class))).thenReturn("<ENCODED>");
		when(userCreatePort.create(any(User.class))).thenReturn(user);

		UserId actual = userSignUpService.signUp(command);

		assertThat(actual).isEqualTo(user.getId());
	}

	@DisplayName("이미 사용중인 이메일이라면 예외가 발생한다.")
	@Test
	void alreadyUseEmail() {
		UserSignUpCommand command = new UserSignUpCommandBuilder().build();

		when(userExistsPort.exists(any(Email.class))).thenReturn(true);

		assertThatThrownBy(() -> userSignUpService.signUp(command)).isInstanceOf(IllegalStateException.class);
	}

	@DisplayName("이미 사용중인 휴대폰 번호라면 예외가 발생한다.")
	@Test
	void alreadyUsePhoneNumber() {
		UserSignUpCommand command = new UserSignUpCommandBuilder().build();

		when(userExistsPort.exists(any(Email.class))).thenReturn(false);
		when(userExistsPort.exists(any(PhoneNumber.class))).thenReturn(true);

		assertThatThrownBy(() -> userSignUpService.signUp(command)).isInstanceOf(IllegalStateException.class);
	}
}
