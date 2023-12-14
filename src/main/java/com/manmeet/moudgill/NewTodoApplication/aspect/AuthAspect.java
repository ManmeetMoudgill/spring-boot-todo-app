package com.manmeet.moudgill.NewTodoApplication.aspect;


import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.dtos.LoginDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UserDto;
import com.manmeet.moudgill.NewTodoApplication.singleton.ApplicationLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAspect {


    @AfterReturning(
            pointcut = "execution(* com.manmeet.moudgill.NewTodoApplication.facades.AuthFacade.login(..)) && args(loginDto)",
            returning = "loginApiResponse")
    public void afterReturningLoginFacadeResponseAdvice(JoinPoint joinPoint,LoginDto loginDto, ResponseEntity<ApiResponse<Object>> loginApiResponse) {
         ApplicationLogger.getInstance().info("Login function has returned the response:::");
    }
}