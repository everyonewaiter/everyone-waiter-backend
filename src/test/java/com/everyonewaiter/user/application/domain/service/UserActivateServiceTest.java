package com.everyonewaiter.user.application.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everyonewaiter.fixture.user.UserActivateCommandBuilder;
import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserStatus;
import com.everyonewaiter.user.application.port.in.command.UserActivateCommand;
import com.everyonewaiter.user.application.port.out.UserFindPort;
import com.everyonewaiter.user.application.port.out.UserStateUpdatePort;

@ExtendWith(MockitoExtension.class)
class UserActivateServiceTest {

	@Mock
	UserFindPort userFindPort;

	@Mock
	UserStateUpdatePort userStateUpdatePort;

	@InjectMocks
	UserActivateService userActivateService;

	@DisplayName("사용자의 상태를 활성화 상태로 변경한다.")
	@Test
	void activate() {
		UserActivateCommand command = new UserActivateCommandBuilder().build();
		User user = new UserBuilder().setStatus(UserStatus.INACTIVE).build();
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

		doReturn(user).when(userFindPort).findOrElseThrow(any(Email.class));

		userActivateService.activate(command);

		verify(userStateUpdatePort, times(1)).update(captor.capture());
		assertThat(captor.getValue().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}
}
