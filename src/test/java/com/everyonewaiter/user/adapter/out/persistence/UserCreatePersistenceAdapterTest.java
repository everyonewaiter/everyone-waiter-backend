package com.everyonewaiter.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.everyonewaiter.PersistenceAdapterTest;
import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.user.application.domain.model.User;

@Import(UserCreatePersistenceAdapter.class)
class UserCreatePersistenceAdapterTest extends PersistenceAdapterTest {

	@Autowired
	UserCreatePersistenceAdapter userCreatePersistenceAdapter;

	@DisplayName("사용자를 생성한다.")
	@Test
	void create() {
		User user = new UserBuilder().build();
		User actual = userCreatePersistenceAdapter.create(user);
		assertThat(actual).isEqualTo(user);
	}
}
