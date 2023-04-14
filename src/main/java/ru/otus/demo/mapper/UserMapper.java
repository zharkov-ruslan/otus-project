package ru.otus.demo.mapper;

import org.mapstruct.*;
import ru.otus.demo.model.dto.UserDto;
import ru.otus.demo.model.entity.UserEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserDto userDto);

    UserDto toDto(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDto userDto, @MappingTarget UserEntity userEntity);
}