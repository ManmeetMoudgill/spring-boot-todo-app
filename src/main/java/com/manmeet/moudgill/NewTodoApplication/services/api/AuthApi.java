package com.manmeet.moudgill.NewTodoApplication.services.api;

import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.dtos.LoginDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UpdatePasswordDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UserDto;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public interface AuthApi {

    String PASSWORD_UPDATE_PATH = "/update/password";
    String LOGIN="/login";
    String SIGNUP="/signup";

    @PostMapping(LOGIN)
    ResponseEntity<ApiResponse<Object>> login(
            @Valid @RequestBody LoginDto loginDto
    );

    @PostMapping(SIGNUP)
    ResponseEntity<ApiResponse<Object>> signup(
            @Valid @RequestBody UserDto userDto
    );

    @PutMapping(PASSWORD_UPDATE_PATH)
    ResponseEntity<ApiResponse<Object>> updatePassword(
            @Valid @RequestBody UpdatePasswordDto updatePasswordDto
    );
}