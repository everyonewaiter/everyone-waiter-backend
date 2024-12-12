package com.everyonewaiter.user.application.domain.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.everyonewaiter.fixture.user.UserBuilder;

class UserTest {

	@DisplayName("회원가입 이벤트를 등록한다.")
	@Test
	void signUp() {
		User user = new UserBuilder().build();

		user.signUp();

		assertThat(user.domainEvents()).hasSize(1);
	}

	@DisplayName("마지막 로그인 시간을 갱신한다.")
	@Test
	void signIn() {
		User user = new UserBuilder().build();
		LocalDateTime now = LocalDateTime.now();

		user.signIn(now);

		assertThat(user.getLastSignInTime()).isEqualTo(now);
	}

	@DisplayName("사용자가 입력하는 권한을 가지고 있는지 확인한다.")
	@MethodSource("hasRoleArgs")
	@ParameterizedTest(name = "[{index}] => 사용자: {0}, 입력: {1}, 결과: {2}")
	void hasRole(UserRole role, UserRole input, boolean expected) {
		User user = new UserBuilder().setRole(role).build();
		assertThat(user.hasRole(input)).isEqualTo(expected);
	}

	static Stream<Arguments> hasRoleArgs() {
		return Stream.of(
			Arguments.of(UserRole.USER, UserRole.USER, true),
			Arguments.of(UserRole.USER, UserRole.OWNER, false),
			Arguments.of(UserRole.USER, UserRole.ADMIN, false),
			Arguments.of(UserRole.OWNER, UserRole.USER, true),
			Arguments.of(UserRole.OWNER, UserRole.OWNER, true),
			Arguments.of(UserRole.OWNER, UserRole.ADMIN, false),
			Arguments.of(UserRole.ADMIN, UserRole.USER, true),
			Arguments.of(UserRole.ADMIN, UserRole.OWNER, true),
			Arguments.of(UserRole.ADMIN, UserRole.ADMIN, true)
		);
	}

	@DisplayName("사용자가 입력하는 권한을 가지고 있지 않은지 확인한다.")
	@MethodSource("lackRoleArgs")
	@ParameterizedTest(name = "[{index}] => 사용자: {0}, 입력: {1}, 결과: {2}")
	void lackRole(UserRole role, UserRole input, boolean expected) {
		User user = new UserBuilder().setRole(role).build();
		assertThat(user.lackRole(input)).isEqualTo(expected);
	}

	static Stream<Arguments> lackRoleArgs() {
		return Stream.of(
			Arguments.of(UserRole.USER, UserRole.USER, false),
			Arguments.of(UserRole.USER, UserRole.OWNER, true),
			Arguments.of(UserRole.USER, UserRole.ADMIN, true),
			Arguments.of(UserRole.OWNER, UserRole.USER, false),
			Arguments.of(UserRole.OWNER, UserRole.OWNER, false),
			Arguments.of(UserRole.OWNER, UserRole.ADMIN, true),
			Arguments.of(UserRole.ADMIN, UserRole.USER, false),
			Arguments.of(UserRole.ADMIN, UserRole.OWNER, false),
			Arguments.of(UserRole.ADMIN, UserRole.ADMIN, false)
		);
	}

	@DisplayName("입력하는 상태와 사용자의 상태가 다른지 검사한다.")
	@EnumSource(value = UserStatus.class, names = {"ACTIVE"}, mode = EnumSource.Mode.EXCLUDE)
	@ParameterizedTest
	void hasDifferentStatus(UserStatus status) {
		User user = new UserBuilder().build();
		assertThat(user.hasDifferentStatus(status)).isTrue();
	}

	@DisplayName("입력하는 상태와 사용자의 상태가 같다면 false를 반환한다.")
	@EnumSource(value = UserStatus.class, names = {"ACTIVE"})
	@ParameterizedTest
	void invertHasDifferentStatus(UserStatus status) {
		User user = new UserBuilder().build();
		assertThat(user.hasDifferentStatus(status)).isFalse();
	}

	@DisplayName("사용자의 권한 이름을 반환한다.")
	@Test
	void getGrantedAuthority() {
		User user = new UserBuilder().build();
		assertThat(user.getGrantedAuthority()).isEqualTo(user.getRole().name());
	}

	@DisplayName("서로 다른 인스턴스여도 id의 값이 같다면 같은 사용자이다.")
	@Test
	void equalsAndHashCode() {
		User user1 = new UserBuilder().setId(new UserId(1L)).build();
		User user2 = new UserBuilder().setId(new UserId(1L)).build();
		assertThat(user1).isEqualTo(user2).hasSameHashCodeAs(user2);
	}
}
