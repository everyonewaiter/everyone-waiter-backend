package com.everyonewaiter.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NativeUserDetails implements UserDetails {

	private final transient User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(user.getGrantedAuthority());
	}

	@Override
	public String getPassword() {
		return user.getPassword().value();
	}

	@Override
	public String getUsername() {
		return user.getEmail().value();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.hasDifferentStatus(UserStatus.INACTIVE);
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.hasDifferentStatus(UserStatus.DELETED);
	}
}
