package com.everyonewaiter.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
class AggregateEventPublisherAspect {

	private final ApplicationEventPublisher applicationEventPublisher;

	@Pointcut("within(@org.springframework.stereotype.Repository *)")
	void repository() {
	}

	@Pointcut("execution(* *..create*(..))")
	void createMethod() {
	}

	@Pointcut("execution(* *..save*(..))")
	void saveMethod() {
	}

	@Pointcut("execution(* *..update*(..))")
	void updateMethod() {
	}

	@Pointcut("execution(* *..delete*(..))")
	void deleteMethod() {
	}

	@AfterReturning(value = "repository() && (createMethod() || saveMethod() || updateMethod() || deleteMethod())")
	void invoke(final JoinPoint joinPoint) {
		for (final Object arg : joinPoint.getArgs()) {
			if (arg instanceof final AggregateRoot root) {
				root.domainEvents().forEach(applicationEventPublisher::publishEvent);
				root.clearDomainEvents();
			}
		}
	}
}
