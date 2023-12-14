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

        HashMap<String, TodoResponseDto> todoHasMap = new HashMap<>();
        todoHasMap.put("todo", todoCreated);

        Response<Object> response = new Response<>();
        response.setData(todoHasMap);
        response.setMessage("Todo has been created successfully!");

        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    public ResponseEntity<ApiResponse<Object>> updateTodo(TodoDto todoDto, Long userId) {
        TodoResponseDto todoUpdated = this.todoDomain.updateTodo(todoDto, userId);

        HashMap<String, TodoResponseDto> todoHasMap = new HashMap<>();
        todoHasMap.put("todo", todoUpdated);

        Response<Object> response = new Response<>();
        response.setData(todoHasMap);
        response.setMessage("Todo has been updated successfully!!");

        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


    }

    public ResponseEntity<ApiResponse<Object>> deleteTodo(Long todoId, Long userId) {
        this.todoDomain.deleteTodo(todoId, userId);

        HashMap<String, HashMap<String, Long>> todoHasMap = new HashMap<>();

        HashMap<String, Long> todoDeleted = new HashMap<>();

        todoDeleted.put("todoId", todoId);
        todoHasMap.put("todo", todoDeleted);

        Response<Object> response = new Response<>();
        response.setData(todoHasMap);
        response.setMessage("Todo has has been deleted successfully!!");

        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    public ResponseEntity<ApiResponse<Object>> getTodoOfLoggedUser(Long todoId, Long userId) {
        TodoResponseDto todoOfLoggedUser = this.todoDomain.getTodoOfLoggedUser(todoId, userId);

        HashMap<String, TodoResponseDto> todoHasMap = new HashMap<>();
        todoHasMap.put("todo", todoOfLoggedUser);

        Response<Object> response = new Response<>();
        response.setData(todoHasMap);
        response.setMessage("Operation went successful!!");

        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    public ResponseEntity<ApiResponse<Object>> getTodosOfLoggedUser(Long userId,Integer pageNumber,Integer pageSize) {
        HashMap<String,Object> todosOfLoggedUser = this.todoDomain.getTodosOfLoggedUser(userId,pageNumber,pageSize);
        HashMap<String, Object> todoHasMap = new HashMap<>();



        todoHasMap.put("todos", todosOfLoggedUser.get("list"));
        todoHasMap.put("pageInfo",todosOfLoggedUser.get("pageInfo"));

        Response<Object> response = new Response<>();
        response.setData(todoHasMap);
        response.setMessage("Operation went successful!!");


        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    public ResponseEntity<ApiResponse<Object>> markTodoCompleted(Long todoId,Long loggedUserId) {

        Long todoMarkedId=this.todoDomain.markTodoCompleted(todoId,loggedUserId);

        HashMap<String, HashMap<String, Long>> todoHasMap = new HashMap<>();

        HashMap<String, Long> todoMarkedCompleted = new HashMap<>();

        todoMarkedCompleted.put("todoId", todoMarkedId);
        todoHasMap.put("todo", todoMarkedCompleted);


        Response<Object> response = new Response<>();
        response.setData(todoHasMap);
        response.setMessage("Todo has has marked as completed successfully!!");


        ApiResponse<Object> apiResponse = ApiResponse.builder().errors(null).response(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);


}

}
