package com.everyonewaiter.authentication.adapter.in.web;

import static com.everyonewaiter.authentication.adapter.in.web.AuthenticationCodeGenerateController.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.everyonewaiter.RestControllerTest;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.authentication.application.port.in.AuthenticationAttemptCountIncrementUseCase;
import com.everyonewaiter.authentication.application.port.in.AuthenticationCodeGenerateUseCase;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;

@WebMvcTest(AuthenticationCodeGenerateController.class)
class AuthenticationCodeGenerateControllerTest extends RestControllerTest {

	@MockitoBean
	AuthenticationAttemptCountIncrementUseCase authenticationAttemptCountIncrementUseCase;

	@MockitoBean
	AuthenticationCodeGenerateUseCase authenticationCodeGenerateUseCase;

	@DisplayName("인증 번호를 생성한다.")
	@Test
	void generate() throws Exception {
		AuthenticationCodeGenerateRequest request = new AuthenticationCodeGenerateRequestBuilder().build();
		doNothing().when(authenticationAttemptCountIncrementUseCase).increment(any());
		doNothing().when(authenticationCodeGenerateUseCase).generate(any());

		mockMvc.perform(post("/api/authentication/code")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertToJsonString(request)))
			.andExpect(status().isNoContent())
			.andDo(document("authentication-code-generate",
				requestFields(
					fieldWithPath("phoneNumber").type(STRING).description("01012345678 형식의 7~8자 휴대폰 번호"),
					fieldWithPath("purpose").type(STRING)
						.description("인증 목적: " + Arrays.toString(AuthenticationPurpose.values()))
				)
			));
	}

	static class AuthenticationCodeGenerateRequestBuilder {

		private final String phoneNumber = new PhoneNumberBuilder().build().value();
		private final String purpose = AuthenticationPurpose.USER_SIGN_UP.name();

		AuthenticationCodeGenerateRequest build() {
			return new AuthenticationCodeGenerateRequest(phoneNumber, purpose);
		}
	}
}
