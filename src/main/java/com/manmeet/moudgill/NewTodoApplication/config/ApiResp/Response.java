package com.manmeet.moudgill.NewTodoApplication.config.ApiResp;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Response <T>{

    private T data;
    private String message;

}
