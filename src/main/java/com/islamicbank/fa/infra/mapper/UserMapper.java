package com.islamicbank.fa.infra.mapper;

import com.islamicbank.fa.api.model.request.ChangePasswordRequest;
import com.islamicbank.fa.api.model.request.CreateUserRequest;
import com.islamicbank.fa.api.model.response.UserResponse;
import com.islamicbank.fa.infra.util.UuidUtil;
import com.islamicbank.fa.repository.entity.Role;
import com.islamicbank.fa.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Mapper(componentModel = "spring",
        imports = UuidUtil.class)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "tokenExpiryDate", ignore = true)
    @Mapping(target = "password",
            expression = "java( passwordEncoder.encode(request.password()) )")
    @Mapping(target = "userUuid", expression = "java( UuidUtil.newUuid() )")
    public abstract User toUser(CreateUserRequest request);

    public abstract UserResponse toView(User user);

    protected String[] map(Set<Role> authorities) {
        return authorities
                .stream()
                .map(Role::getAuthority)
                .toArray(String[]::new);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "userUuid", ignore = true)
    @Mapping(target = "refreshToken", expression = "java( null )")
    @Mapping(target = "tokenExpiryDate", expression = "java( null )")
    @Mapping(target = "password", expression = "java( passwordEncoder.encode(request.newPassword()) )")
    public abstract void updatePassword(ChangePasswordRequest request, @MappingTarget User user);
}
