package com.everyonewaiter.user.application.domain.model;

import static com.everyonewaiter.common.PreconditionChecker.*;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.everyonewaiter.security.Encodable;

public record RawPassword(String value) implements Encodable {

	private static final String PASSWORD_REGEX =
		"^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-+=`~])[\\w!@#$%^&*()\\-+=`~]{8,}$";
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

	public RawPassword {
		require(StringUtils.hasText(value), () -> "require.password.not.blank");
		require(PASSWORD_PATTERN.matcher(value).matches(), () -> "require.password.format");
	}

	@Override
	public String getRawText() {
		return value;
	}
}
