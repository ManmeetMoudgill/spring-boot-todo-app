package com.manmeet.moudgill.NewTodoApplication.utils;

import com.manmeet.moudgill.NewTodoApplication.dtos.AuthResponseDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TodoDtoToResponseDtoConverter {
    /**
     * @param todoDto
     * @return
     * @Description TodoSerice helper method tpo convert the TodoDto format to TodoResponseDtoFormat
     */

    public TodoResponseDto convertFromTodoDtoToTodoResponseDto(TodoDto todoDto) {
        AuthResponseDto authResponseDto = AuthResponseDto.builder().email(todoDto.getTodoUser().getEmail()).userRole(todoDto.getTodoUser().getUserRole()).userId(todoDto.getTodoUser().getUserId()).username(todoDto.getTodoUser().getUsername()).build();
        return TodoResponseDto.builder().todoTitle(todoDto.getTodoTitle()).todoId(todoDto.getTodoId()).todoDescription(todoDto.getTodoDescription()).todoCreatedDate(todoDto.getTodoCreatedDate()).todoCompletedDate(todoDto.getTodoCompletedDate()).todoUser(authResponseDto).build();
    }
}
