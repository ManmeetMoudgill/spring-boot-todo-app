package com.manmeet.moudgill.NewTodoApplication.dtos;

import com.manmeet.moudgill.NewTodoApplication.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    private Long userId;

    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    private String token;
}
