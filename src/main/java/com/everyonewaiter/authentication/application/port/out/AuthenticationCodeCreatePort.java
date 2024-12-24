package com.everyonewaiter.authentication.application.port.out;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;

public interface AuthenticationCodeCreatePort {

	void create(AuthenticationCode authenticationCode);
}
