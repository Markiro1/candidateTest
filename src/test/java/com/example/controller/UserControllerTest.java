package com.example.controller;

import com.candidatetestspecification.controller.AuthController;
import com.candidatetestspecification.controller.UserController;
import com.candidatetestspecification.dtos.jwt.JwtRequest;
import com.candidatetestspecification.dtos.jwt.JwtResponse;
import com.candidatetestspecification.dtos.user.UserRequestDto;
import com.candidatetestspecification.dtos.user.UserResponseDto;
import com.candidatetestspecification.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private AuthController authController;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void authenticateTest() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("username", "password");
        String jwtToken = "jwtToken";
        JwtResponse jwtResponse = new JwtResponse(jwtToken);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(jwtResponse);

        doReturn(expectedResponse).when(authController).createAuthToken(any(JwtRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(jwtToken));
    }

    @Test
    void createUserTest() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername("username");

        Mockito.when(userService.save(any(UserRequestDto.class))).thenReturn(userResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(userResponseDto)))
                .andExpect(jsonPath("$.username").value("username"));
    }
}
