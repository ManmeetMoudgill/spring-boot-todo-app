package com.manmeet.moudgill.NewTodoApplication.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordDto {

    @NotNull
    @NotBlank(message = "Username is blank or consists only of whitespace characters!!")
    @Size(min = 10, max = 50, message = "Username should not exceed 50 characters and must have least 10 characters !!")
    private String username;

    @NotNull
    @NotBlank(message = "New password is blank or consists only of whitespace characters !!!")
    private String newPassword;


    @NotBlank(message = "Confirm password is blank or consists only of whitespace characters !!!")
    @NotNull
    private String newConfirmPassword;
}
