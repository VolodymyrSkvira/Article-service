package com.example.articleservice.service.impl;

import com.example.articleservice.dto.user.UserRegistrationRequestDto;
import com.example.articleservice.dto.user.UserResponseDto;
import com.example.articleservice.exception.RegistrationException;
import com.example.articleservice.mapper.UserMapper;
import com.example.articleservice.model.user.RoleName;
import com.example.articleservice.model.user.User;
import com.example.articleservice.repository.RoleRepository;
import com.example.articleservice.repository.UserRepository;
import com.example.articleservice.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(
            UserRegistrationRequestDto request) throws RegistrationException {
        if (userRepository.existsByEmail(request.email())) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleRepository.findByName(RoleName.USER)));
        return userMapper.toUserResponseDto(userRepository.save(user));
    }
}
