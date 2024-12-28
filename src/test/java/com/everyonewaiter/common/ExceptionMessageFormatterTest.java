package com.everyonewaiter.common;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExceptionMessageFormatterTest {

	@DisplayName("인자를 전달받아 메시지 소스 문자열 형태로 변환한다.")
	@Test
	void formatWithArg() {
		String formattedMessage = format("message", "handwoong@gmail.com");
		assertThat(formattedMessage).isEqualTo("message {handwoong@gmail.com} [String]");
	}

	@DisplayName("인자와 설명을 전달받아 메시지 소스 문자열 형태로 변환한다.")
	@Test
	void formatWithArgAndDetail() {
		String formattedMessage = format("message", "handwoong@gmail.com", "Email");
		assertThat(formattedMessage).isEqualTo("message {handwoong@gmail.com} [Email]");
	}

	@DisplayName("인자 목록을 전달받아 메시지 소스 문자열 형태로 변환한다.")
	@Test
	void formatWithArgs() {
		List<Object> arguments = List.of("handwoong@gmail.com", 1);
		String formattedMessage = ExceptionMessageFormatter.format("message", arguments);
		assertThat(formattedMessage).isEqualTo("message {handwoong@gmail.com, 1} [String, Integer]");
	}

	@DisplayName("인자 목록과 설명 목록을 전달받아 메시지 소스 문자열 형태로 변환한다.")
	@Test
	void formatWithArgsAndDetails() {
		List<Object> arguments = List.of("handwoong@gmail.com", 20);
		List<String> details = List.of("이메일", "나이");
		String formattedMessage = ExceptionMessageFormatter.format("message", arguments, details);
		assertThat(formattedMessage).isEqualTo("message {handwoong@gmail.com, 20} [이메일, 나이]");
	}
}
