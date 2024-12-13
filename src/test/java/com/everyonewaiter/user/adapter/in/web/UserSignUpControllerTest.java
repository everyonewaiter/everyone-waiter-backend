package com.everyonewaiter.user.adapter.in.web;

import static com.everyonewaiter.user.adapter.in.web.UserSignUpController.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.everyonewaiter.RestControllerTest;
import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.fixture.user.RawPasswordBuilder;
import com.everyonewaiter.user.application.domain.model.UserId;
import com.everyonewaiter.user.application.port.in.UserSignUpUseCase;

@WebMvcTest(UserSignUpController.class)
class UserSignUpControllerTest extends RestControllerTest {

	@MockitoBean
	UserSignUpUseCase userSignUpUseCase;

	@DisplayName("사용자가 가입한다.")
	@Test
	void signUp() throws Exception {
		Long result = 1L;

		when(userSignUpUseCase.signUp(any())).thenReturn(new UserId(result));

		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertToJsonString(new UserSignUpRequestBuilder().build())))
			.andExpect(status().isCreated())
			.andExpect(header().string(HttpHeaders.LOCATION, result.toString()))
			.andDo(document("user-sign-up",
				requestFields(
					fieldWithPath("email").type(STRING).description("이메일"),
					fieldWithPath("password").type(STRING).description("특수문자, 숫자, 영문자를 포함한 8자 이상의 비밀번호"),
					fieldWithPath("phoneNumber").type(STRING).description("01012345678 형식의 7~8자 휴대폰 번호")
				),
				responseHeaders(headerWithName("Location").description("생성된 사용자의 ID"))
			));
	}

	static class UserSignUpRequestBuilder {

		private final String email = new EmailBuilder().build().value();
		private final String password = new RawPasswordBuilder().build().value();
		private final String phoneNumber = new PhoneNumberBuilder().build().value();

		UserSignUpRequest build() {
			return new UserSignUpRequest(email, password, phoneNumber);
		}
	}
}
