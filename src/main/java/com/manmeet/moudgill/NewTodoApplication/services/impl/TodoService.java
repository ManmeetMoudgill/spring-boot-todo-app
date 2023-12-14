package com.manmeet.moudgill.NewTodoApplication.services.impl;

import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.config.security.JwtHelper;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoDto;
import com.manmeet.moudgill.NewTodoApplication.facades.TodoFacade;
import com.manmeet.moudgill.NewTodoApplication.services.api.TodoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TodoService implements TodoApi {


    private final TodoFacade todoFacade;
    private final JwtHelper jwtHelper;

    @Autowired
    private TodoService(TodoFacade todoFacade, JwtHelper jwtHelper) {
        this.todoFacade = todoFacade;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> createTodo(TodoDto todoDto, String authHeader) {

        Long loggedUserId = this.jwtHelper.extractUserId(authHeader.substring(7));

        return this.todoFacade.createTodo(todoDto, loggedUserId);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> updateTodo(TodoDto todoDto, Long todoId, String authHeader) {
        Long loggedUserId = this.jwtHelper.extractUserId(authHeader.substring(7));

        todoDto.setTodoId(todoId);
        return this.todoFacade.updateTodo(todoDto, loggedUserId);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> deleteTodo(Long todoId, String authHeader) {
        Long loggedUserId = this.jwtHelper.extractUserId(authHeader.substring(7));
        return this.todoFacade.deleteTodo(todoId, loggedUserId);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getTodoOfLoggedUser(Long todoId, String authHeader) {

        Long loggedUserId = this.jwtHelper.extractUserId(authHeader.substring(7));
        return this.todoFacade.getTodoOfLoggedUser(todoId, loggedUserId);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getTodosOfLoggedUser(String authHeader,Integer pageNumber,Integer pageSize) {
        Long loggedUserId = this.jwtHelper.extractUserId(authHeader.substring(7));
        return this.todoFacade.getTodosOfLoggedUser(loggedUserId,pageNumber,pageSize);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> markTodoAsCompleted(Long todoId,String authHeader) {
        Long loggedUserId = this.jwtHelper.extractUserId(authHeader.substring(7));
        return this.todoFacade.markTodoCompleted(todoId,loggedUserId);
    }
}
