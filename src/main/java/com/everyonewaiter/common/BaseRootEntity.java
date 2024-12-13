package com.everyonewaiter.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public abstract class BaseRootEntity {

	@Id
	protected final Long id;
	protected final LocalDateTime createdAt;

	@LastModifiedDate
	protected final LocalDateTime updatedAt;
}
