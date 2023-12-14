package com.manmeet.moudgill.NewTodoApplication.config.ApiResp;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {

    private Integer pageSize;

    private Integer pageNumber;

    private Integer totalPages;

}
