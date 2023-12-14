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
import org.springframework.http.HttpStatus;
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
        UserDto loggedUser = this.authDomain.login(loginDto);

        AuthResponseDto loggedUserAuthResponse = AuthResponseDto.builder().email(loggedUser.getEmail()).userId(loggedUser.getUserId()).username(loggedUser.getUsername()).userRole(loggedUser.getUserRole()).token(loggedUser.getToken()).build();

        HashMap<String, AuthResponseDto> userHashMap = new HashMap<>();
        userHashMap.put("user", loggedUserAuthResponse);


        Response<Object> response = new Response<>();
        response.setData(userHashMap);
        response.setMessage("Login operation went successfully!!");

        HttpHeaders headers = this.cookiesConfig.configure("user_token", loggedUser.getToken(), false, "/");

        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(apiResponse);


    }

    public ResponseEntity<ApiResponse<Object>> signup(UserDto userDto) {
        UserDto userCreated = this.authDomain.signup(userDto);

        AuthResponseDto createdUserAuthResponse = AuthResponseDto.builder().email(userCreated.getEmail()).userId(userCreated.getUserId()).username(userCreated.getUsername()).userRole(userCreated.getUserRole()).token(userCreated.getToken()).build();

        HashMap<String, AuthResponseDto> userHashMap = new HashMap<>();
        userHashMap.put("user", createdUserAuthResponse);

        Response<Object> response = new Response<>();
        response.setData(userHashMap);
        response.setMessage("Sing up operation went successfully!!");

        HttpHeaders headers = this.cookiesConfig.configure("user_token", userCreated.getToken(), false, "/");

        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(apiResponse);
    }


    public ResponseEntity<ApiResponse<Object>> updatePassword(UpdatePasswordDto updatePasswordDto) {
        UserDto userUpdated = this.authDomain.updatePassword(updatePasswordDto);


        AuthResponseDto updatedUserAuthResponse = AuthResponseDto.builder().email(userUpdated.getEmail()).userId(userUpdated.getUserId()).username(userUpdated.getUsername()).userRole(userUpdated.getUserRole()).token(userUpdated.getToken()).build();


        HashMap<String, AuthResponseDto> userHashMap = new HashMap<>();
        userHashMap.put("user", updatedUserAuthResponse);

        Response<Object> response = new Response<>();
        response.setData(userHashMap);
        response.setMessage("Password has been successfully updated!!");

        HttpHeaders headers = this.cookiesConfig.configure("user_token", userUpdated.getToken(), false, "/");

        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(apiResponse);

    }
}
