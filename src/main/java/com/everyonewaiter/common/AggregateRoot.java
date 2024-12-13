package com.everyonewaiter.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AggregateRoot {

	private final List<Object> domainEvents = new ArrayList<>();

	protected void registerEvent(Object event) {
		domainEvents.add(event);
	}

	protected void clearDomainEvents() {
		domainEvents.clear();
	}

	public Collection<Object> domainEvents() {
		return Collections.unmodifiableCollection(domainEvents);
	}
}
