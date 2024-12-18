package com.everyonewaiter.mail.adapter.in.web;

import static com.everyonewaiter.mail.adapter.in.web.EmailAuthenticationMailController.*;
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
import com.everyonewaiter.mail.application.port.in.EmailAuthenticationMailUseCase;

@WebMvcTest(EmailAuthenticationMailController.class)
class EmailAuthenticationMailControllerTest extends RestControllerTest {

	@MockitoBean
	EmailAuthenticationMailUseCase emailAuthenticationMailUseCase;

	@DisplayName("이메일 인증 메일을 발송한다.")
	@Test
	void sendEmailAuthenticationMail() throws Exception {
		doNothing().when(emailAuthenticationMailUseCase).sendEmailAuthenticationMail(any());

		mockMvc.perform(post("/api/mail/authentication")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertToJsonString(new EmailAuthenticationMailRequestBuilder().build())))
			.andExpect(status().isNoContent())
			.andDo(document("mail-email-authentication",
				requestFields(fieldWithPath("email").type(STRING).description("이메일"))
			));
	}

	static class EmailAuthenticationMailRequestBuilder {

		private final String email = new EmailBuilder().build().value();

		public EmailAuthenticationMailRequest build() {
			return new EmailAuthenticationMailRequest(email);
		}
	}
}
