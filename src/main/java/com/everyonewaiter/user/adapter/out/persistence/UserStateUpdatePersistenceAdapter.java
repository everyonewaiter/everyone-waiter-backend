package com.everyonewaiter.user.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.port.out.UserStateUpdatePort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class UserStateUpdatePersistenceAdapter implements UserStateUpdatePort {

	private final UserJdbcRepository userJdbcRepository;
	private final UserMapper userMapper;

	@Override
	public void update(User user) {
		userJdbcRepository.save(userMapper.mapToEntity(user));
	}
}