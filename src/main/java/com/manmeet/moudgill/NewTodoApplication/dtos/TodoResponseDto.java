package com.manmeet.moudgill.NewTodoApplication.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {
    private Long todoId;

    private String todoTitle;

    private String todoDescription;

    private LocalDateTime todoCreatedDate;

    private LocalDateTime todoCompletedDate;

    private AuthResponseDto todoUser;

}