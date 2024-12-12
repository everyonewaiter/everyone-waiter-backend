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
	private Email email = new EmailBuilder().build();
	private EncodedPassword password = new EncodedPasswordBuilder().build();
	private PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
	private UserRole role = UserRole.USER;
	private UserStatus status = UserStatus.ACTIVE;
	private final LocalDateTime lastLoggedIn = LocalDateTime.now();
	private final LocalDateTime createdAt = LocalDateTime.now();
	private final LocalDateTime updatedAt = LocalDateTime.now();

	public User build() {
		return new User(id, createdAt, updatedAt, email, password, phoneNumber, role, status, lastLoggedIn);
	}

	public UserBuilder setId(UserId id) {
		this.id = id;
		return this;
	}

	public UserBuilder setEmail(Email email) {
		this.email = email;
		return this;
	}

	public UserBuilder setPassword(EncodedPassword password) {
		this.password = password;
		return this;
	}

	public UserBuilder setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public UserBuilder setRole(UserRole role) {
		this.role = role;
		return this;
	}

	public UserBuilder setStatus(UserStatus status) {
		this.status = status;
		return this;
	}
}
