package ru.otus.demo;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class OtusProjectApplication {

    @GetMapping(value = "health")
    public StatusDto health() {
        return StatusDto.builder().status("OK").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(OtusProjectApplication.class, args);
    }

    @Data
    @Builder
    public static class StatusDto {
        private String status;
    }

}
