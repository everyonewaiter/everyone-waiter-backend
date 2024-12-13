package com.everyonewaiter.user.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.port.out.UserExistsPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class UserExistsPersistenceAdapter implements UserExistsPort {

	private final UserJdbcRepository userJdbcRepository;

	@Override
	public boolean exists(Email email) {
		return userJdbcRepository.existsByEmail(email.value());
	}

	@Override
	public boolean exists(PhoneNumber phoneNumber) {
		return userJdbcRepository.existsByPhoneNumber(phoneNumber.value());
	}
}
