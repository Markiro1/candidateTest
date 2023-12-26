package com.candidatetestspecification.service;

import com.candidatetestspecification.dtos.user.UserResponseDto;
import com.candidatetestspecification.dtos.user.UserRequestDto;

public interface UserService {
    UserResponseDto save(UserRequestDto userDto);
}
