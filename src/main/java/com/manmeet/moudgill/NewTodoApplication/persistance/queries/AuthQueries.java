package com.manmeet.moudgill.NewTodoApplication.persistance.queries;

public class AuthQueries {

    public static final String UPDATE_THE_USER_PASSWORD = "UPDATE User u SET u.password = :newPassword, u.confirmPassword =:newConfirmPassword WHERE u.username = :username";
}
