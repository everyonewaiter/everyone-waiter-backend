package com.everyonewaiter.user.adapter.out.persistence;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Table;

import com.everyonewaiter.common.BaseRootEntity;
import com.everyonewaiter.user.application.domain.model.UserRole;
import com.everyonewaiter.user.application.domain.model.UserStatus;

import lombok.Getter;

@Getter
@Table(name = "user")
class UserEntity extends BaseRootEntity {

	private final String email;
	private final String password;
	private final String phoneNumber;
	private final UserRole role;
	private final UserStatus status;
	private final LocalDateTime lastLoggedIn;

	UserEntity(
		Long id,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		String email,
		String password,
		String phoneNumber,
		UserRole role,
		UserStatus status,
		LocalDateTime lastLoggedIn
	) {
		super(id, createdAt, updatedAt);
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.status = status;
		this.lastLoggedIn = lastLoggedIn;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
