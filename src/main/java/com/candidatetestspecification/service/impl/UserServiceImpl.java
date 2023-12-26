package com.candidatetestspecification.service.impl;

import com.candidatetestspecification.dtos.user.UserResponseDto;
import com.candidatetestspecification.dtos.user.UserRequestDto;
import com.candidatetestspecification.entities.User;
import com.candidatetestspecification.repository.UserRepository;
import com.candidatetestspecification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto save(UserRequestDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userRepo.save(user), UserResponseDto.class);
    }

}
