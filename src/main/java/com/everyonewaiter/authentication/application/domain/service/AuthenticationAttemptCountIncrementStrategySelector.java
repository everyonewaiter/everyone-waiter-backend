package com.everyonewaiter.authentication.application.domain.service;

import static com.everyonewaiter.common.PreconditionChecker.*;

import java.util.EnumMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountIncrementStrategy;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;

@Component
class AuthenticationAttemptCountIncrementStrategySelector {

	private final EnumMap<AuthenticationPurpose, AuthenticationAttemptCountIncrementStrategy> strategies;

	AuthenticationAttemptCountIncrementStrategySelector(List<AuthenticationAttemptCountIncrementStrategy> strategies) {
		Assert.notNull(strategies, "Strategies must not be null");
		check(strategies.size() == AuthenticationPurpose.values().length, () -> "구현되어 있지 않은 전략이 있습니다.");

		this.strategies = new EnumMap<>(AuthenticationPurpose.class);
		strategies.forEach(strategy -> this.strategies.put(strategy.getPurpose(), strategy));
	}

	AuthenticationAttemptCountIncrementStrategy getStrategy(AuthenticationPurpose purpose) {
		return strategies.get(purpose);
	}
}
