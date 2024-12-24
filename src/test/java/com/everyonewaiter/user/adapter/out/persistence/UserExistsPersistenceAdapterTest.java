package com.everyonewaiter.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

import com.everyonewaiter.PersistenceAdapterTest;
import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

@Import(UserExistsPersistenceAdapter.class)
class UserExistsPersistenceAdapterTest extends PersistenceAdapterTest {

	@Autowired
	JdbcAggregateTemplate jdbcAggregateTemplate;

	@Autowired
	UserExistsPersistenceAdapter userExistsPersistenceAdapter;

	@Autowired
	UserMapper userMapper;

	@BeforeEach
	void setUp() {
		UserEntity userEntity = userMapper.mapToEntity(new UserBuilder().build());
		jdbcAggregateTemplate.insert(userEntity);
	}

	@DisplayName("이메일로 사용자가 존재하는지 확인한다.")
	@MethodSource("existsByEmailArgs")
	@ParameterizedTest(name = "[{index}] => 이메일: {0}, 결과: {1}")
	void existsByEmail(String email, boolean expected) {
		boolean actual = userExistsPersistenceAdapter.exists(new Email(email));
		assertThat(actual).isEqualTo(expected);
	}

	static Stream<Arguments> existsByEmailArgs() {
		return Stream.of(
			Arguments.of("handwoong@gmail.com", true),
			Arguments.of("handwoong@naver.com", false)
		);
	}

	@DisplayName("휴대폰 번호로 사용자가 존재하는지 확인한다.")
	@MethodSource("existsByPhoneNumberArgs")
	@ParameterizedTest(name = "[{index}] => 이메일: {0}, 결과: {1}")
	void existsByPhoneNumber(String phoneNumber, boolean expected) {
		boolean actual = userExistsPersistenceAdapter.exists(new PhoneNumber(phoneNumber));
		assertThat(actual).isEqualTo(expected);
	}

	static Stream<Arguments> existsByPhoneNumberArgs() {
		return Stream.of(
			Arguments.of("01012345678", true),
			Arguments.of("01087654321", false)
		);
	}
}
