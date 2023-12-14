package com.manmeet.moudgill.NewTodoApplication.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfiguration {


    @Bean
    public BCryptPasswordEncoder encodePassword() {
       return new BCryptPasswordEncoder();

    }

    public boolean verifyHashedPassword(String userInputPassword, String storedHashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(userInputPassword, storedHashedPassword);
    }

    public boolean confirmPasswords(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) return true;
        return password.equals(confirmPassword);
    }
}
