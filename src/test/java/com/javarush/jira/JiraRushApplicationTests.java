package com.javarush.jira;

import com.javarush.jira.common.config.PGContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
class JiraRushApplicationTests {
	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer;

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		postgreSQLContainer = PGContainer.getInstance();
		postgreSQLContainer.start();
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}
	@Test
	void contextLoads() {
	}
}
