package com.manmeet.moudgill.NewTodoApplication.facades;

import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.PageInfo;
import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.Response;
import com.manmeet.moudgill.NewTodoApplication.domains.TodoDomainImpl;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class TodoFacade {

    private final TodoDomainImpl todoDomain;

    @Autowired
    public TodoFacade(TodoDomainImpl todoDomain) {
        this.todoDomain = todoDomain;
    }

    public ResponseEntity<ApiResponse<Object>> createTodo(TodoDto todoDto, Long userId) {
        TodoResponseDto todoCreated = this.todoDomain.createTodo(todoDto, userId);
        return createResponseEntity("Todo has been created successfully!", "todo", todoCreated);
    }

    public ResponseEntity<ApiResponse<Object>> updateTodo(TodoDto todoDto, Long userId) {
        TodoResponseDto todoUpdated = this.todoDomain.updateTodo(todoDto, userId);
        return createResponseEntity("Todo has been updated successfully!!", "todo", todoUpdated);
    }

    public ResponseEntity<ApiResponse<Object>> deleteTodo(Long todoId, Long userId) {
        this.todoDomain.deleteTodo(todoId, userId);
        return createResponseEntity("Todo has been deleted successfully!!", "todoId", todoId);
    }

    public ResponseEntity<ApiResponse<Object>> getTodoOfLoggedUser(Long todoId, Long userId) {
        TodoResponseDto todoOfLoggedUser = this.todoDomain.getTodoOfLoggedUser(todoId, userId);
        return createResponseEntity("Operation went successful!!", "todo", todoOfLoggedUser);
    }

    public ResponseEntity<ApiResponse<Object>> getTodosOfLoggedUser(Long userId, Integer pageNumber, Integer pageSize) {
        var todosOfLoggedUser = this.todoDomain.getTodosOfLoggedUser(userId, pageNumber, pageSize);
        var todoHasMap = new HashMap<String, Object>();
        todoHasMap.put("list", todosOfLoggedUser.get("list"));
        todoHasMap.put("pageInfo", todosOfLoggedUser.get("pageInfo"));
        return createResponseEntity("Operation went successful!!", "todos", todoHasMap);
    }

    public ResponseEntity<ApiResponse<Object>> markTodoCompleted(Long todoId, Long loggedUserId) {
        Long todoMarkedId = this.todoDomain.markTodoCompleted(todoId, loggedUserId);
        return createResponseEntity("Todo has been marked as completed successfully!!", "todoId", todoMarkedId);
    }

    private ResponseEntity<ApiResponse<Object>> createResponseEntity(String message, String key, Object value) {
        var todoHasMap = createTodoHashMap(key, value);
        var response = new Response<Object>();
        response.setData(todoHasMap);
        response.setMessage(message);
        var apiResponse = ApiResponse.builder().errors(null).response(response).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    private HashMap<String, Object> createTodoHashMap(String key, Object value) {
        var todoHasMap = new HashMap<String, Object>();
        todoHasMap.put(key, value);
        return todoHasMap;
    }
}