package com.manmeet.moudgill.NewTodoApplication.services.impl;

import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.dtos.LoginDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UpdatePasswordDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UserDto;
import com.manmeet.moudgill.NewTodoApplication.facades.AuthFacade;
import com.manmeet.moudgill.NewTodoApplication.services.api.AuthApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class AuthService implements AuthApi {

    private final AuthFacade authFacade;


    @Autowired
    public AuthService(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> login(LoginDto loginDto) {
        return this.authFacade.login(loginDto);

    }

    @Override
    public ResponseEntity<ApiResponse<Object>> signup(UserDto userDto) {
        return this.authFacade.signup(userDto);
    }

    public ResponseEntity<ApiResponse<Object>> updatePassword(UpdatePasswordDto updatePasswordDto) {
        return this.authFacade.updatePassword(updatePasswordDto);
    }
}
