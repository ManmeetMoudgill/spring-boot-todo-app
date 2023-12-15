package com.manmeet.moudgill.NewTodoApplication.aspect;


import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.dtos.AuthResponseDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.LoginDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UserDto;
import com.manmeet.moudgill.NewTodoApplication.singleton.ApplicationLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

@Aspect
@Component
public class AuthAspect {


    @AfterReturning(
            pointcut = "execution(* com.manmeet.moudgill.NewTodoApplication.facades.AuthFacade.login(..)) && args(loginDto)"
            )
    public void afterReturningLoginFacadeResponseAdvice(JoinPoint joinPoint,LoginDto loginDto) {
         ApplicationLogger.getInstance().info("Login function has returned the response---->>");
         ApplicationLogger.getInstance().info("User: "+loginDto.getUsername()+" Logged in");
    }


    @Before("execution(* com.manmeet.moudgill.NewTodoApplication.services.impl.*.*(..))")
    public void beforeEveryServiceMethodAdvice(JoinPoint joinPoint) {
        ApplicationLogger.getInstance().info("Following method:-->"+joinPoint.getSignature().getDeclaringTypeName()+" has been called");
    }
}