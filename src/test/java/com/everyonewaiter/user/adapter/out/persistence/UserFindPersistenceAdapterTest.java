package com.everyonewaiter.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

import com.everyonewaiter.PersistenceAdapterTest;
import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;

@PersistenceAdapterTest
@Import(UserFindPersistenceAdapter.class)
class UserFindPersistenceAdapterTest {

	@Autowired
	JdbcAggregateTemplate jdbcAggregateTemplate;

	@Autowired
	UserFindPersistenceAdapter userFindPersistenceAdapter;

	@Autowired
	UserMapper userMapper;

	@BeforeEach
	void setUp() {
		UserEntity userEntity = userMapper.mapToEntity(new UserBuilder().build());
		jdbcAggregateTemplate.insert(userEntity);
	}

	@DisplayName("이메일로 사용자를 조회한다.")
	@Test
	void findByEmail() {
		Email email = new EmailBuilder().setEmail("handwoong@gmail.com").build();

		Optional<User> user = userFindPersistenceAdapter.find(email);

		assertThat(user).isPresent();
	}

	@DisplayName("이메일로 사용자를 조회하지 못하면 비어있는 Optional을 반환한다.")
	@Test
	void notFoundUserByEmail() {
		Email email = new EmailBuilder().setEmail("handwoong@naver.com").build();

		Optional<User> user = userFindPersistenceAdapter.find(email);

		assertThat(user).isEmpty();
	}
}
