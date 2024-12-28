package com.everyonewaiter;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;

import com.everyonewaiter.common.PersistenceMapper;

@ActiveProfiles("test")
@DataJdbcTest(includeFilters = {@ComponentScan.Filter(classes = {PersistenceMapper.class})})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class PersistenceAdapterTest {

	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4.3");

	static {
		mysql.start();
	}

	@BeforeAll
	static void setUp() {
		System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
		System.setProperty("spring.datasource.username", mysql.getUsername());
		System.setProperty("spring.datasource.password", mysql.getPassword());
		System.setProperty("spring.datasource.driver-class-name", mysql.getDriverClassName());
	}
}
