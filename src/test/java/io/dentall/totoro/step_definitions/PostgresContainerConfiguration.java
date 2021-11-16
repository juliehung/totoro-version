package io.dentall.totoro.step_definitions;

import org.testcontainers.containers.PostgreSQLContainer;


public class PostgresContainerConfiguration {

    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
        .withDatabaseName("totoro")
        .withUsername("totoro")
        .withPassword("totoro");

    public static void initTestContainers() {
        postgreSQLContainer.setCommand("postgres", "-c", "fsync=off", "-c", "shared_buffers=512MB");
        postgreSQLContainer.start();
    }

    public static String getJdbcUrl() {
        return postgreSQLContainer.getJdbcUrl();
    }

    public static String getUsername() {
        return postgreSQLContainer.getUsername();
    }

    public static String getPassword() {
        return postgreSQLContainer.getPassword();
    }

}
