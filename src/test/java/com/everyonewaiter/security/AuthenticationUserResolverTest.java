package com.everyonewaiter.security;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.NativeWebRequest;

import com.everyonewaiter.fixture.security.AuthenticationUserBuilder;
import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserRole;
import com.everyonewaiter.user.application.port.out.UserFindPort;

@ExtendWith(MockitoExtension.class)
class AuthenticationUserResolverTest {

	private static final String BEARER_TOKEN = "Bearer TOKEN";

	@Mock
	JwtProvider jwtProvider;

	@Mock
	UserFindPort userFindPort;

	@Mock
	NativeWebRequest request;

	@Mock
	MethodParameter parameter;

	@InjectMocks
	AuthenticationUserResolver authenticationUserResolver;

	@DisplayName("파라미터의 어노테이션 지원 여부를 검사한다.")
	@MethodSource("supportParameterArgs")
	@ParameterizedTest(name = "메서드 명: {0}, 결과: {1}")
	void supportParameter(String methodName, boolean expected) throws NoSuchMethodException {
		Method method = TestAuthenticationUserController.class.getDeclaredMethod(methodName, User.class);
		MethodParameter methodParameter = MethodParameter.forExecutable(method, 0);
		assertThat(authenticationUserResolver.supportsParameter(methodParameter)).isEqualTo(expected);
	}

	static Stream<Arguments> supportParameterArgs() {
		return Stream.of(
			Arguments.of("user", true),
			Arguments.of("admin", true),
			Arguments.of("owner", true),
			Arguments.of("anonymous", false)
		);
	}

	@DisplayName("모든 유효성 검사를 통과하면 유저 인스턴스를 반환한다.")
	@MethodSource("resolveUserArgs")
	@ParameterizedTest(name = "[{index}] => 권한: {0}")
	void resolveUser(UserRole role, AuthenticationUser authenticationUser) {
		User user = new UserBuilder().setRole(role).build();

		when(request.getHeader(any())).thenReturn(BEARER_TOKEN);
		when(jwtProvider.decode(any())).thenReturn(Optional.of(user.getEmail()));
		when(userFindPort.find(any(Email.class))).thenReturn(Optional.of(user));
		when(parameter.getParameterAnnotation(AuthenticationUser.class)).thenReturn(authenticationUser);

		User actual = authenticationUserResolver.resolveArgument(parameter, null, request, null);

		assertThat(actual).isEqualTo(user);
	}

	static Stream<Arguments> resolveUserArgs() {
		return Stream.of(
			Arguments.of(UserRole.USER, new AuthenticationUserBuilder().build()),
			Arguments.of(UserRole.OWNER, new AuthenticationUserBuilder().setOwner(true).build()),
			Arguments.of(UserRole.ADMIN, new AuthenticationUserBuilder().setAdministrator(true).build())
		);
	}

	@SuppressWarnings("unused")
	@DisplayName("사용자의 필요 권한 검사에 실패한 경우 권한 예외가 발생한다.")
	@MethodSource("failResolveUserArgs")
	@ParameterizedTest(name = "[{index}] => 권한: {0}, 필요 권한: {2}")
	void failResolve(UserRole role, AuthenticationUser authenticationUser, UserRole permission) {
		User user = new UserBuilder().setRole(role).build();

		when(request.getHeader(any())).thenReturn(BEARER_TOKEN);
		when(jwtProvider.decode(any())).thenReturn(Optional.of(user.getEmail()));
		when(userFindPort.find(any(Email.class))).thenReturn(Optional.of(user));
		when(parameter.getParameterAnnotation(AuthenticationUser.class)).thenReturn(authenticationUser);

		assertThatThrownBy(() -> authenticationUserResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AccessDeniedException.class);
	}

	static Stream<Arguments> failResolveUserArgs() {
		return Stream.of(
			Arguments.of(UserRole.USER, new AuthenticationUserBuilder().setOwner(true).build(), UserRole.OWNER),
			Arguments.of(UserRole.USER, new AuthenticationUserBuilder().setAdministrator(true).build(), UserRole.ADMIN),
			Arguments.of(UserRole.OWNER, new AuthenticationUserBuilder().setAdministrator(true).build(), UserRole.ADMIN)
		);
	}

	@DisplayName("요청에서 토큰을 추출할 수 없는 경우 인증 예외가 발생한다.")
	@Test
	void failExtractToken() {
		when(request.getHeader(any())).thenReturn(null);
		assertThatThrownBy(() -> authenticationUserResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AuthenticationException.class);
	}

	@DisplayName("토큰이 유효하지 않은 경우 인증 예외가 발생한다.")
	@Test
	void invalidToken() {
		when(request.getHeader(any())).thenReturn(BEARER_TOKEN);
		when(jwtProvider.decode(any())).thenReturn(Optional.empty());
		assertThatThrownBy(() -> authenticationUserResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AuthenticationException.class);
	}

	@DisplayName("사용자를 찾지 못한다면 인증 예외가 발생한다.")
	@Test
	void notFoundUser() {
		Email email = new EmailBuilder().build();

		when(request.getHeader(any())).thenReturn(BEARER_TOKEN);
		when(jwtProvider.decode(any())).thenReturn(Optional.of(email));
		when(userFindPort.find(any(Email.class))).thenReturn(Optional.empty());

		assertThatThrownBy(() -> authenticationUserResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AuthenticationException.class);
	}

	@SuppressWarnings({"unused", "squid:S1186"})
	static class TestAuthenticationUserController {

		void user(@AuthenticationUser User user) {
		}

		void admin(@AuthenticationUser(administrator = true) User user) {
		}

		void owner(@AuthenticationUser(owner = true) User user) {
		}

		void anonymous(User user) {
		}
	}
}
