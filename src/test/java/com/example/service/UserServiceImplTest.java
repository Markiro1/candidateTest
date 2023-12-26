package com.example.service;

import com.candidatetestspecification.dtos.user.UserRequestDto;
import com.candidatetestspecification.dtos.user.UserResponseDto;
import com.candidatetestspecification.entities.User;
import com.candidatetestspecification.repository.UserRepository;
import com.candidatetestspecification.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @Test
    void saveTest() {
        UserRequestDto userRequestDto = new UserRequestDto("username", "password");
        String encodedPassword = "encodedPassword";
        String userPassword = userRequestDto.getPassword();
        User user = new User();
        UserResponseDto userResponseDto = new UserResponseDto();

        Mockito.when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn(encodedPassword);
        Mockito.when(modelMapper.map(userRequestDto, User.class)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto response = userService.save(userRequestDto);

        Assertions.assertNotNull(response);

        Mockito.verify(passwordEncoder).encode(userPassword);
        Mockito.verify(modelMapper).map(userRequestDto, User.class);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(modelMapper).map(user, UserResponseDto.class);
    }
}
