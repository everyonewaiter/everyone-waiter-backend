package com.everyonewaiter.fixture.user;

import com.everyonewaiter.user.application.domain.Email;
import com.everyonewaiter.user.application.domain.User;
import com.everyonewaiter.user.application.domain.UserRole;

public class UserBuilder {

	private Email email = new EmailBuilder().build();
	private UserRole role = UserRole.USER;

	public User build() {
		return new User(email, role);
	}

	public UserBuilder setEmail(Email email) {
		this.email = email;
		return this;
	}

	public UserBuilder setRole(UserRole role) {
		this.role = role;
		return this;
	}
}
