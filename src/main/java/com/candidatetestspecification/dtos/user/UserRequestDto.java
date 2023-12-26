package com.candidatetestspecification.dtos.user;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UserRequestDto {

    private String username;
    private String password;
}
