package com.manmeet.moudgill.NewTodoApplication.persistance.queries;

public class TokenQueries {

    public static final String FIND_TOKEN_BY_USER_ID_AND_BY_TOKEN = "select t from Token t where t.tokenUser.userId = :userId and t.tokenContent = :tokenContent";

}
