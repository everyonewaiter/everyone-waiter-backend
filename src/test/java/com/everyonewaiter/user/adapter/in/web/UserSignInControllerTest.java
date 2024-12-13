package com.everyonewaiter.user.adapter.in.web;

import static com.everyonewaiter.user.adapter.in.web.UserSignInController.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.everyonewaiter.RestControllerTest;
import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.fixture.user.RawPasswordBuilder;
import com.everyonewaiter.user.application.port.in.UserSignInUseCase;
import com.everyonewaiter.user.application.port.in.response.JwtResponse;

@WebMvcTest(UserSignInController.class)
class UserSignInControllerTest extends RestControllerTest {

	@MockitoBean
	UserSignInUseCase userSignInUseCase;

	@DisplayName("사용자가 로그인하면 액세스 토큰을 발급한다.")
	@Test
	void signIn() throws Exception {
		JwtResponse result = new JwtResponse(VALID_TOKEN);

		when(userSignInUseCase.signIn(any())).thenReturn(result);

		mockMvc.perform(post("/api/users/sign-in")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertToJsonString(new UserSignInRequestBuilder().build())))
			.andExpect(status().isOk())
			.andExpect(content().json(convertToJsonString(result)))
			.andDo(document("user-sign-in",
				requestFields(
					fieldWithPath("email").type(STRING).description("이메일"),
					fieldWithPath("password").type(STRING).description("특수문자, 숫자, 영문자를 포함한 8자 이상의 비밀번호")
				),
				responseFields(fieldWithPath("accessToken").type(STRING).description("액세스 토큰"))
			));
	}

	static class UserSignInRequestBuilder {

		private final String email = new EmailBuilder().build().value();
		private final String password = new RawPasswordBuilder().build().value();

		UserSignInRequest build() {
			return new UserSignInRequest(email, password);
		}
	}
}
