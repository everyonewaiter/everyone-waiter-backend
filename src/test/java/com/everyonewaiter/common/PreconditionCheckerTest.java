package com.everyonewaiter.common;

import static com.everyonewaiter.common.PreconditionChecker.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PreconditionCheckerTest {

	@DisplayName("조건이 참이라면 메시지 함수는 실행되지 않는다.")
	@Test
	void lazyMessage() {
		TestString calculator = mock(TestString.class);
		require(true, () -> String.valueOf(calculator.concat("Hello", "World")));
		check(true, () -> String.valueOf(calculator.concat("Hello", "World")));
		verify(calculator, never()).concat(anyString(), anyString());
	}

	static class TestString {

		public String concat(String str1, String str2) {
			return str1 + str2;
		}
	}
}
