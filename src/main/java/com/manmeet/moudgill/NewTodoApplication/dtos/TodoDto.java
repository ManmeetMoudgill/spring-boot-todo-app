package com.manmeet.moudgill.NewTodoApplication.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TodoDto {

    private Long todoId;

    @NotEmpty(message = "Title should be set !!")
    @NotBlank(message = "Title is blank or consists only of whitespace characters!!!")
    private String todoTitle;

    @NotEmpty(message = "Description should be set !!")
    @NotBlank(message = "Description is blank or consists only of whitespace characters!!!")
    private String todoDescription;

    private LocalDateTime todoCreatedDate;

    private LocalDateTime todoCompletedDate;

    private UserDto todoUser;


}
