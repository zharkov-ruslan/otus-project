package ru.otus.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.demo.model.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}