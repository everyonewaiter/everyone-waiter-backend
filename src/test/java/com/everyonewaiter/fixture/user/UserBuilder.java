package com.everyonewaiter.fixture.user;

import java.time.LocalDateTime;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.EncodedPassword;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserId;
import com.everyonewaiter.user.application.domain.model.UserRole;
import com.everyonewaiter.user.application.domain.model.UserStatus;

public class UserBuilder {

	private UserId id = new UserId();
	private final Email email = new EmailBuilder().build();
	private final EncodedPassword password = new EncodedPasswordBuilder().build();
	private final PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
	private UserRole role = UserRole.USER;
	private final UserStatus status = UserStatus.ACTIVE;
	private final LocalDateTime lastLoggedIn = LocalDateTime.now();
	private final LocalDateTime createdAt = LocalDateTime.now();
	private final LocalDateTime updatedAt = LocalDateTime.now();

	public User build() {
		return new User(id, email, password, phoneNumber, role, status, lastLoggedIn, createdAt, updatedAt);
	}

	public UserBuilder setId(UserId id) {
		this.id = id;
		return this;
	}

	public UserBuilder setRole(UserRole role) {
		this.role = role;
		return this;
	}
}
