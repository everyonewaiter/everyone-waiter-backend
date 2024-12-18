package com.everyonewaiter.user.adapter.in.web;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.everyonewaiter.RestControllerTest;
import com.everyonewaiter.user.application.port.in.UserActivateUseCase;

@WebMvcTest(UserActivateController.class)
class UserActivateControllerTest extends RestControllerTest {

	@MockitoBean
	UserActivateUseCase userActivateUseCase;

	@DisplayName("사용자의 상태를 활성화 상태로 변경한다.")
	@Test
	void activate() throws Exception {
		doNothing().when(userActivateUseCase).activate(any());

		mockMvc.perform(patch("/api/users/activate")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, bearerToken()))
			.andExpect(status().isNoContent())
			.andDo(document("user-activate"));
	}
}
