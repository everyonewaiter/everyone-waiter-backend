package com.everyonewaiter.authentication.application.port.out;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountKey;

public interface AuthenticationAttemptCountIncrementPort {

	void increment(AuthenticationAttemptCountKey key);
}
