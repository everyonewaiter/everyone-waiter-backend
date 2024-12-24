package com.everyonewaiter.authentication.application.port.out;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationCodeKey;

public interface AuthenticationCodeFindPort {

	AuthenticationCode findOrElseThrow(AuthenticationCodeKey key);
}
