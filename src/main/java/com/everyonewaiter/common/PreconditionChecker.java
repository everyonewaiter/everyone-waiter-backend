package com.everyonewaiter.common;

import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PreconditionChecker {

	public static void require(boolean condition, Supplier<String> message) {
		if (!condition) {
			throw new IllegalArgumentException(message.get());
		}
	}

	public static void check(boolean condition, Supplier<String> message) {
		if (!condition) {
			throw new IllegalStateException(message.get());
		}
	}
}
