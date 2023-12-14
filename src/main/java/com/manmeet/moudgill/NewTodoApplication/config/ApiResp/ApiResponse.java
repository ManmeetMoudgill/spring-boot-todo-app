package com.manmeet.moudgill.NewTodoApplication.config.ApiResp;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
public class ApiResponse <T> {


    private Response<T> response;

    private List<Error> errors=new ArrayList<>();




}
