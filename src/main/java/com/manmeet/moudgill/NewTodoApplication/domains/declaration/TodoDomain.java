package com.manmeet.moudgill.NewTodoApplication.domains.declaration;

import com.manmeet.moudgill.NewTodoApplication.dtos.TodoDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoResponseDto;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.List;

public interface TodoDomain {


    TodoResponseDto createTodo(TodoDto todoDto, Long userId);

    TodoResponseDto updateTodo(TodoDto todoDto,Long userId);

    Long deleteTodo(Long todoId,Long userId);

    TodoResponseDto getTodoOfLoggedUser(Long todoId,Long userId);

    HashMap<String,Object> getTodosOfLoggedUser(Long userId,Integer pageNumber,Integer pageSize);

    Long markTodoCompleted(Long todoId,Long loggedUserId);

}
