package com.everyonewaiter.authentication.adapter.in.web;

import static com.everyonewaiter.authentication.adapter.in.web.AuthenticationCodeVerifyController.*;
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
import com.everyonewaiter.authentication.application.port.in.AuthenticationCodeVerifyUseCase;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;

@WebMvcTest(AuthenticationCodeVerifyController.class)
class AuthenticationCodeVerifyControllerTest extends RestControllerTest {

	@MockitoBean
	AuthenticationCodeVerifyUseCase authenticationCodeVerifyUseCase;

	@DisplayName("인증번호를 검증한다.")
	@Test
	void verify() throws Exception {
		AuthenticationCodeVerifyRequest request = new AuthenticationCodeVerifyRequestBuilder().build();
		doNothing().when(authenticationCodeVerifyUseCase).verify(any());

		mockMvc.perform(post("/api/authentication/code/verify")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertToJsonString(request)))
			.andExpect(status().isNoContent())
			.andDo(document("authentication-code-verify",
				requestFields(
					fieldWithPath("phoneNumber").type(STRING).description("01012345678 형식의 7~8자 휴대폰 번호"),
					fieldWithPath("purpose").type(STRING)
						.description("인증 목적: " + Arrays.toString(AuthenticationPurpose.values())),
					fieldWithPath("code").type(NUMBER).description("사용자가 입력한 인증 번호")
				)
			));
	}

	static class AuthenticationCodeVerifyRequestBuilder {

		private final String phoneNumber = new PhoneNumberBuilder().build().value();
		private final String purpose = AuthenticationPurpose.USER_SIGN_UP.name();

		AuthenticationCodeVerifyRequest build() {
			return new AuthenticationCodeVerifyRequest(phoneNumber, purpose, 123456);
		}
	}
}
