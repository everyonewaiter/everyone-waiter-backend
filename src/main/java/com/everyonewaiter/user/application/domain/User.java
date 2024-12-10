package com.everyonewaiter.user.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

	private final Email email;
	private UserRole role;

	public boolean hasRole(UserRole role) {
		return this.role.compareTo(role) >= 0;
	}

	public boolean lackRole(UserRole role) {
		return !hasRole(role);
	}
}
