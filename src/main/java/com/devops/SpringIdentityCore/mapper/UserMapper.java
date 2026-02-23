package com.devops.SpringIdentityCore.mapper;


import com.devops.SpringIdentityCore.dtos.requests.UserRegistrationRequest;
import com.devops.SpringIdentityCore.dtos.requests.UserUpdateRequest;
import com.devops.SpringIdentityCore.dtos.responses.UserResponse;
import com.devops.SpringIdentityCore.model.User;
import org.mapstruct.*;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toResponse(User user);

    @Mapping(target = "email", source = "email", qualifiedByName = "toLowerCase")
    User toEntity(UserRegistrationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateRequest dto, @MappingTarget User user);

    @Named("toLowerCase")
    default String toLowerCase(String email) {
        return email != null ? email.toLowerCase().trim() : null;
    }
}
