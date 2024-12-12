package com.everyonewaiter.user.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

interface UserJdbcRepository extends ListCrudRepository<UserEntity, Long> {

	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	Optional<UserEntity> findByEmail(String email);
}
