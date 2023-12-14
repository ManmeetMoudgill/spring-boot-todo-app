package com.manmeet.moudgill.NewTodoApplication.domains.declaration;

import com.manmeet.moudgill.NewTodoApplication.dtos.UpdatePasswordDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.LoginDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UserDto;

public interface AuthDomain {

    UserDto login(LoginDto loginDto);

    UserDto signup(UserDto userDto);


    UserDto updatePassword(UpdatePasswordDto updatePasswordDto);



}
