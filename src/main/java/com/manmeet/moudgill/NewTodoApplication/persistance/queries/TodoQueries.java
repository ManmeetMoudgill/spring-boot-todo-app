package com.manmeet.moudgill.NewTodoApplication.persistance.queries;

public class TodoQueries {
    public static final String GET_TODOS_OF_USER_BY_USERID = "select t from Todo t where t.todoUser.userId=:userId ORDER BY t.todoId ASC";
    public static final String GET_SINGLE_TODO_OF_USER_BY_USERID = "select t from Todo t where t.todoUser.userId=:userId AND t.todoId=:todoId";

}
