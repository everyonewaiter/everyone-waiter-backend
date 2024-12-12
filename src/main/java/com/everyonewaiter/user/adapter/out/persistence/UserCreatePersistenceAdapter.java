package com.everyonewaiter.user.adapter.out.persistence;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.port.out.UserCreatePort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class UserCreatePersistenceAdapter implements UserCreatePort {

	private final JdbcAggregateTemplate jdbcAggregateTemplate;
	private final UserMapper userMapper;

	@Override
	public User create(User user) {
		UserEntity userEntity = jdbcAggregateTemplate.insert(userMapper.mapToEntity(user));
		return userMapper.mapToDomain(userEntity);
	}
}
