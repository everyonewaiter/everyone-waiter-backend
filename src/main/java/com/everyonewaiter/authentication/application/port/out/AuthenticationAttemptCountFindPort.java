package com.everyonewaiter.authentication.application.port.out;

import java.util.Optional;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCount;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountKey;

public interface AuthenticationAttemptCountFindPort {

	Optional<AuthenticationAttemptCount> find(AuthenticationAttemptCountKey key);
}
