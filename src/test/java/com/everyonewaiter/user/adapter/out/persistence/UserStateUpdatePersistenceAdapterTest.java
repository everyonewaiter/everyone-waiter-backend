package com.everyonewaiter.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

import com.everyonewaiter.PersistenceAdapterTest;
import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.EncodedPassword;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserId;
import com.everyonewaiter.user.application.domain.model.UserStatus;

@Import(UserStateUpdatePersistenceAdapter.class)
class UserStateUpdatePersistenceAdapterTest extends PersistenceAdapterTest {

	@Autowired
	JdbcAggregateTemplate jdbcAggregateTemplate;

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserStateUpdatePersistenceAdapter userStateUpdatePersistenceAdapter;

	@BeforeEach
	void setUp() {
		UserEntity userEntity = userMapper.mapToEntity(new UserBuilder().setId(new UserId(1L)).build());
		jdbcAggregateTemplate.insert(userEntity);
	}

	@DisplayName("사용자 정보를 변경한다.")
	@Test
	void update() {
		Long userId = 1L;
		String email = "handwoong@naver.com";
		String password = "<PASSWORD>";
		String phoneNumber = "01087654321";
		UserStatus status = UserStatus.DELETED;

		User user = new UserBuilder().setId(new UserId(userId))
			.setEmail(new Email(email))
			.setPassword(new EncodedPassword(password))
			.setPhoneNumber(new PhoneNumber(phoneNumber))
			.setStatus(status)
			.build();

		userStateUpdatePersistenceAdapter.update(user);

		UserEntity actual = jdbcAggregateTemplate.findById(userId, UserEntity.class);
		assertThat(actual.getEmail()).isEqualTo(email);
		assertThat(actual.getPassword()).isEqualTo(password);
		assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
		assertThat(actual.getStatus()).isEqualTo(status);
	}
}
