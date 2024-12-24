package com.everyonewaiter.user.application.domain.model;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;
import static com.everyonewaiter.common.PreconditionChecker.*;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public record PhoneNumber(String value) {

	private static final String PHONE_NUMBER_REGEX = "^01[016789]\\d{7,8}$";
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

	public PhoneNumber {
		require(StringUtils.hasText(value), () -> "require.phone-number.not.blank");
		require(PHONE_NUMBER_PATTERN.matcher(value).matches(), () -> format("require.phone-number.format", value));
	}

	@Override
	public String toString() {
		return value;
	}
}
