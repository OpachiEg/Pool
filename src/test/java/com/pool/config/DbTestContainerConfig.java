package com.pool.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class DbTestContainerConfig {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:13.1-alpine");

}
