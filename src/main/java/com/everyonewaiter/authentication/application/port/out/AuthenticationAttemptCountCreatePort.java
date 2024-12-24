package com.everyonewaiter.authentication.application.port.out;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCount;

public interface AuthenticationAttemptCountCreatePort {

	void create(AuthenticationAttemptCount authenticationAttemptCount);
}
