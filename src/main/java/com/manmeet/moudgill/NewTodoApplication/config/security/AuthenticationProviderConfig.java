package com.manmeet.moudgill.NewTodoApplication.config.security;


import com.manmeet.moudgill.NewTodoApplication.utils.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderConfig {


    private final CustomUserDetailService customUserDetailService;
    private final PasswordConfiguration passwordConfiguration;

    @Autowired
    public AuthenticationProviderConfig(CustomUserDetailService customUserDetailService,PasswordConfiguration passwordConfiguration){
        this.customUserDetailService=customUserDetailService;
        this.passwordConfiguration=passwordConfiguration;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordConfiguration.encodePassword());
        return authenticationProvider;
    }







}
