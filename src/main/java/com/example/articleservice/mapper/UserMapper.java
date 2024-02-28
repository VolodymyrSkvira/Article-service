package com.example.articleservice.mapper;

import com.example.articleservice.dto.user.UserRegistrationRequestDto;
import com.example.articleservice.dto.user.UserResponseDto;
import com.example.articleservice.model.user.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl"
)
public interface UserMapper {
    User toUser(UserRegistrationRequestDto requestDto);

    UserResponseDto toUserResponseDto(User user);
}
