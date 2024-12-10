package com.everyonewaiter.common;

import java.sql.SQLException;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6SpyOptions;

class P6SpyEventListener extends JdbcEventListener {

	@Override
	public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException exception) {
		P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpyFormatter.class.getName());
	}
}
