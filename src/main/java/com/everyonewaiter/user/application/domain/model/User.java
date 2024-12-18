package com.everyonewaiter.user.application.domain.model;

import static com.everyonewaiter.user.application.domain.model.UserRole.*;
import static com.everyonewaiter.user.application.domain.model.UserStatus.*;

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
	private LocalDateTime lastSignInTime;

	public static User create(Email email, EncodedPassword password, PhoneNumber phoneNumber) {
		LocalDateTime now = LocalDateTime.now();
		User user = new User(new UserId(), now, now, email, password, phoneNumber, USER, INACTIVE, now);
		user.registerEvent(new UserSignUpEvent(email));
		return user;
	}

	public void signIn(LocalDateTime now) {
		this.lastSignInTime = now;
	}

	public boolean hasRole(UserRole role) {
		return this.role.compareTo(role) >= 0;
	}

	public boolean lackRole(UserRole role) {
		return !hasRole(role);
	}

	public boolean isInactive() {
		return this.status == INACTIVE;
	}

	public boolean hasDifferentStatus(UserStatus status) {
		return this.status != status;
	}

	public String getGrantedAuthority() {
		return this.role.name();
	}
}
