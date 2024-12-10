package com.everyonewaiter.user.application.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.everyonewaiter.common.AggregateRoot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User extends AggregateRoot {

	private final UserId id;

	private Email email;
	private EncodedPassword password;
	private PhoneNumber phoneNumber;
	private UserRole role;
	private UserStatus status;
	private LocalDateTime lastLoggedIn;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public void register() {
		registerEvent(new UserRegisterEvent(email));
	}

	public void login(LocalDateTime now) {
		this.lastLoggedIn = now;
	}

	public boolean hasRole(UserRole role) {
		return this.role.compareTo(role) >= 0;
	}

	public boolean lackRole(UserRole role) {
		return !hasRole(role);
	}

	public boolean hasDifferentStatus(UserStatus status) {
		return this.status != status;
	}

	public String getGrantedAuthority() {
		return this.role.name();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User user)) {
			return false;
		}
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
