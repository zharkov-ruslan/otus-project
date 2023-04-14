package ru.otus.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Configuration
@Testcontainers
@EnableConfigurationProperties
@Profile({"docker-postgres-test"})
public class ConfigEmbeddedPostgresDocker {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public JdbcDatabaseContainer<?> jdbcDatabaseContainer(
            @NonNull final DockerPostgresImageProperties dockerPostgresImageProperties) {
        return new PostgreSQLContainer<>(dockerPostgresImageProperties.getUrlByPriority())
                .waitingFor(Wait.forListeningPort());
    }

    @Bean
    public DataSource dataSource(@NonNull final JdbcDatabaseContainer<?> jdbcDatabaseContainer) {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcDatabaseContainer.getJdbcUrl());
        hikariConfig.setUsername(jdbcDatabaseContainer.getUsername());
        hikariConfig.setPassword(jdbcDatabaseContainer.getPassword());
        return new HikariDataSource(hikariConfig);
    }
}