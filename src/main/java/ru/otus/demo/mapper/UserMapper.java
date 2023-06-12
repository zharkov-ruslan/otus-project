package ru.otus.demo.mapper;

import lombok.NonNull;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import ru.otus.demo.model.dto.UserDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    String USER_ATTRIBUTES_MAPPING = "userAttributesMapping";
    String USER_CREDENTIALS_MAPPING = "userCredentialsMapping";
    String USER_ATTRIBUTES_MERGE = "userAttributeMerge";

    @Mapping(target = "username", source = "userName")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "attributes", source = "userDto", qualifiedByName = USER_ATTRIBUTES_MAPPING)
    @Mapping(target = "credentials", source = "password", qualifiedByName = USER_CREDENTIALS_MAPPING)
    @Mapping(target = "enabled", constant = "true")
    @NotNull
    UserRepresentation toUserRepresentation(@NotNull UserDto userDto);

    @Mapping(target = "userName", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", expression = "java(userRepresentation.firstAttribute(\"phone\"))")
    @Mapping(target = "password", ignore = true)
    @NotNull
    UserDto toUserDto(@NotNull UserRepresentation userRepresentation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "attributes", expression = "java(userAttributeMerge(source, target))")
    @NotNull
    UserRepresentation partialUpdate(@NotNull UserRepresentation source,
                                     @NotNull @MappingTarget UserRepresentation target);

    @Named(USER_ATTRIBUTES_MAPPING)
    @NonNull
    default Map<String, List<String>> userAttributesMapping(@NonNull final UserDto userDto) {
        return Map.of("phone", List.of(userDto.getPhone()));
    }

    @Named(USER_CREDENTIALS_MAPPING)
    @Nullable
    default List<CredentialRepresentation> userCredentialsMapping(@Nullable final String password) {
        if (isBlank(password)) {
            return null;
        }
        final CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return List.of(credential);
    }

    @Named(USER_ATTRIBUTES_MERGE)
    @Nullable
    default Map<String, List<String>> userAttributeMerge(@NonNull final UserRepresentation source,
                                                         @NonNull final UserRepresentation target) {
        final Map<String, List<String>> targetAttributes = target.getAttributes();
        if (CollectionUtils.isEmpty(targetAttributes)) {
            return source.getAttributes();
        } else {
            targetAttributes.putAll(source.getAttributes());
            return targetAttributes;
        }
    }
}