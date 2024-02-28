package com.example.articleservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.articleservice.dto.user.UserRegistrationRequestDto;
import com.example.articleservice.dto.user.UserResponseDto;
import com.example.articleservice.exception.RegistrationException;
import com.example.articleservice.mapper.UserMapper;
import com.example.articleservice.mapper.impl.UserMapperImpl;
import com.example.articleservice.model.user.Role;
import com.example.articleservice.model.user.RoleName;
import com.example.articleservice.model.user.User;
import com.example.articleservice.repository.RoleRepository;
import com.example.articleservice.repository.UserRepository;
import com.example.articleservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Register valid user")
    public void register_WithValidData_ShouldReturnUserResponseDto() {
        Long id = 1L;

        Role role = new Role();
        role.setId(id);
        role.setName(RoleName.USER);

        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("12345678");

        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "test@example.com",
                "12345678",
                "12345678",
                "John",
                "Doe"
        );

        UserResponseDto expected = new UserResponseDto(
                id,
                "test@example.com",
                "John",
                "Doe"
        );

        when(userMapper.toUser(requestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponseDto(user)).thenReturn(expected);
        when(roleRepository.findByName(RoleName.USER)).thenReturn(role);

        UserResponseDto actual = userService.register(requestDto);

        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Register invalid user")
    public void register_WithInvalidData_ShouldThrowException() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("12345678");

        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "test@example.com",
                "12345678",
                "12345678",
                "John",
                "Doe"
        );

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        Exception exception = assertThrows(
                RegistrationException.class,
                () -> userService.register(requestDto)
        );
        String expected = "Unable to complete registration";
        String actual = exception.getMessage();
        assertEquals(actual, expected);

    }
}
