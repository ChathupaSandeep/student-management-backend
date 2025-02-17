package com.Academa.student_management;

import org.testcontainers.containers.PostgreSQLContainer;

class StudentManagementApplicationTests {
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("student")
			.withUsername("postgres")
			.withPassword("abc123");

	static {
		postgreSQLContainer.start();
	}

	public static PostgreSQLContainer<?> getInstance() {
		return postgreSQLContainer;
	}

}
