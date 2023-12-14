package com.manmeet.moudgill.NewTodoApplication.services.api;


import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoDto;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public interface TodoApi {

    String TODO_ID_PATH_VARIABLE = "todoId";
    String AUTHORIZATION_HEADER = "Authorization";
    String PAGE_NUMBER_PARAM = "pageNumber";
    String PAGE_SIZE_PARAM = "pageSize";

    @PostMapping("/create")
    ResponseEntity<ApiResponse<Object>> createTodo(
            @Valid @RequestBody TodoDto todoDto,
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader
    );

    @PutMapping("/update/{" + TODO_ID_PATH_VARIABLE + "}")
    ResponseEntity<ApiResponse<Object>> updateTodo(
            @Valid @RequestBody TodoDto todoDto,
            @PathVariable(TODO_ID_PATH_VARIABLE) Long todoId,
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader
    );

    @DeleteMapping("/delete/{" + TODO_ID_PATH_VARIABLE + "}")
    ResponseEntity<ApiResponse<Object>> deleteTodo(
            @PathVariable(TODO_ID_PATH_VARIABLE) Long todoId,
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader
    );

    @GetMapping("/{" + TODO_ID_PATH_VARIABLE + "}")
    ResponseEntity<ApiResponse<Object>> getTodoOfLoggedUser(
            @PathVariable(TODO_ID_PATH_VARIABLE) Long todoId,
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader
    );

    @GetMapping("/all")
    ResponseEntity<ApiResponse<Object>> getTodosOfLoggedUser(
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader,
            @RequestParam(name = PAGE_NUMBER_PARAM, required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "10") Integer pageSize
    );

    @GetMapping("/complete/{" + TODO_ID_PATH_VARIABLE + "}")
    ResponseEntity<ApiResponse<Object>> markTodoAsCompleted(
            @PathVariable(TODO_ID_PATH_VARIABLE) Long todoId,
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader
    );
}

