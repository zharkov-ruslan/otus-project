package ru.otus.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.utility.DockerImageName;

@Configuration
@ConfigurationProperties(prefix = "test.postgres.docker.image")
@Profile("docker-postgres-test")
@Getter
@Setter
public class DockerPostgresImageProperties {
    private String url;

    public DockerImageName getUrlByPriority() {
        return DockerImageName.parse(url).asCompatibleSubstituteFor("postgres");
    }
}