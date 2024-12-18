package com.everyonewaiter.mail.application.port.out;

import com.everyonewaiter.mail.application.domain.model.MailHistory;

public interface MailHistoryCreatePort {

	MailHistory create(MailHistory mailHistory);
}
