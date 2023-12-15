package com.manmeet.moudgill.NewTodoApplication.facades;

import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.Response;
import com.manmeet.moudgill.NewTodoApplication.domains.AuthDomainImpl;
import com.manmeet.moudgill.NewTodoApplication.dtos.AuthResponseDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.LoginDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UpdatePasswordDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UserDto;
import com.manmeet.moudgill.NewTodoApplication.utils.CookiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AuthFacade {

    private final AuthDomainImpl authDomain;
    private final CookiesConfig cookiesConfig;

    @Autowired
    public AuthFacade(AuthDomainImpl authDomain, CookiesConfig cookiesConfig) {
        this.authDomain = authDomain;
        this.cookiesConfig = cookiesConfig;
    }

    public ResponseEntity<ApiResponse<Object>> login(LoginDto loginDto) {
        UserDto loggedUser = authDomain.login(loginDto);
        return buildResponse(loggedUser, "Login operation went successfully!!");
    }

    public ResponseEntity<ApiResponse<Object>> signup(UserDto userDto) {
        UserDto userCreated = authDomain.signup(userDto);
        return buildResponse(userCreated, "Sign up operation went successfully!!");
    }

    public ResponseEntity<ApiResponse<Object>> updatePassword(UpdatePasswordDto updatePasswordDto) {
        UserDto userUpdated = authDomain.updatePassword(updatePasswordDto);
        return buildResponse(userUpdated, "Password has been successfully updated!!");
    }

    private ResponseEntity<ApiResponse<Object>> buildResponse(UserDto user, String message) {
        AuthResponseDto userAuthResponse = AuthResponseDto.builder()
                .email(user.getEmail())
                .userId(user.getUserId())
                .username(user.getUsername())
                .userRole(user.getUserRole())
                .token(user.getToken())
                .build();

        HashMap<String, AuthResponseDto> userHashMap = new HashMap<>();
        userHashMap.put("user", userAuthResponse);

        Response<Object> response = new Response<>();
        response.setData(userHashMap);
        response.setMessage(message);

        HttpHeaders headers = cookiesConfig.configure("user_token", user.getToken(), false, "/");

        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .errors(null)
                .response(response)
                .build();

        return ResponseEntity.ok().headers(headers).body(apiResponse);
    }
}
