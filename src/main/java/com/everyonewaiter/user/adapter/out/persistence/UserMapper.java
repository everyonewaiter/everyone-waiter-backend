package com.everyonewaiter.user.adapter.out.persistence;

import com.everyonewaiter.common.PersistenceMapper;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.EncodedPassword;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserId;

@PersistenceMapper
class UserMapper {

	User mapToDomain(UserEntity userEntity) {
		return new User(
			new UserId(userEntity.getId()),
			userEntity.getCreatedAt(),
			userEntity.getUpdatedAt(),
			new Email(userEntity.getEmail()),
			new EncodedPassword(userEntity.getPassword()),
			new PhoneNumber(userEntity.getPhoneNumber()),
			userEntity.getRole(),
			userEntity.getStatus(),
			userEntity.getLastLoggedIn()
		);
	}

	UserEntity mapToEntity(User user) {
		return new UserEntity(
			user.getId().value(),
			user.getCreatedAt(),
			user.getUpdatedAt(),
			user.getEmail().value(),
			user.getPassword().value(),
			user.getPhoneNumber().value(),
			user.getRole(),
			user.getStatus(),
			user.getLastLoggedIn()
		);
	}
}
