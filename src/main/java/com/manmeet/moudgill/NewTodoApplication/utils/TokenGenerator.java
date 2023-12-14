package com.manmeet.moudgill.NewTodoApplication.utils;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class TokenGenerator {


    public static final String TOKEN_STRING="ASDSADASDSAASD698SAD98ASD698ASD6SAJKBSAD8097YSADJASDJSADJKSADJ";

    public static String generateToken(Integer length) {
        Random random = new Random();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(TokenGenerator.TOKEN_STRING.length());
            token.append(TokenGenerator.TOKEN_STRING.charAt(index));
        }

        return token.toString();
    }
}
