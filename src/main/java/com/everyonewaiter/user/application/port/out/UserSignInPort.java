package com.everyonewaiter.user.application.port.out;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.RawPassword;

public interface UserSignInPort {

	String signIn(Email email, RawPassword password);
}
