package com.everyonewaiter.user.adapter.out.persistence;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.port.out.UserFindPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class UserFindPersistenceAdapter implements UserFindPort {

	private final UserJdbcRepository userJdbcRepository;
	private final UserMapper userMapper;

	@Override
	public Optional<User> find(Email email) {
		return userJdbcRepository.findByEmail(email.value()).map(userMapper::mapToDomain);
	}

	@Override
	public User findOrElseThrow(Email email) {
		return find(email).orElseThrow(() ->
			new NoSuchElementException(format("not.found.user", email.value(), "Email")));
	}
}
