package com.everyonewaiter.user.application.domain.model;

import java.time.LocalDateTime;

import com.everyonewaiter.common.AggregateRoot;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class User extends AggregateRoot {

	private final UserId id;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	private Email email;
	private EncodedPassword password;
	private PhoneNumber phoneNumber;
	private UserRole role;
	private UserStatus status;
	private LocalDateTime lastLoggedIn;

	public static User create(Email email, EncodedPassword password, PhoneNumber phoneNumber) {
		LocalDateTime now = LocalDateTime.now();
		return new User(new UserId(), now, now, email, password, phoneNumber, UserRole.USER, UserStatus.INACTIVE, now);
	}

	public void signUp() {
		registerEvent(new UserSignUpEvent(email));
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
}
