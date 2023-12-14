package com.manmeet.moudgill.NewTodoApplication.domains;


import com.manmeet.moudgill.NewTodoApplication.config.security.JwtHelper;
import com.manmeet.moudgill.NewTodoApplication.domains.declaration.AuthDomain;
import com.manmeet.moudgill.NewTodoApplication.dtos.LoginDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UpdatePasswordDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UserDto;
import com.manmeet.moudgill.NewTodoApplication.enums.Role;
import com.manmeet.moudgill.NewTodoApplication.exceptions.ApplicationException;
import com.manmeet.moudgill.NewTodoApplication.persistance.entities.User;
import com.manmeet.moudgill.NewTodoApplication.persistance.respositories.AuthRepo;
import com.manmeet.moudgill.NewTodoApplication.utils.PasswordConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthDomainImpl implements AuthDomain {


    private final PasswordConfiguration passwordConfiguration;
    private final AuthRepo authRepo;


    private final ModelMapper modelMapper;

    private final JwtHelper jwtHelper;


    @Autowired
    public AuthDomainImpl(PasswordConfiguration passwordConfiguration, AuthRepo authRepo, JwtHelper jwtHelper,ModelMapper modelMapper) {
        this.passwordConfiguration = passwordConfiguration;
        this.authRepo = authRepo;
        this.jwtHelper = jwtHelper;
        this.modelMapper=modelMapper;
    }


    @Override
    public UserDto login(LoginDto loginDto) {
        User user = this.authRepo.findByUsername(loginDto.getUsername()).orElseThrow(() -> new ApplicationException("User not found!!"));

        if (!this.passwordConfiguration.verifyHashedPassword(loginDto.getPassword(), user.getPassword())) {
            throw new ApplicationException("Incorrect Credentials!!");
        }

        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        userDto.setToken(this.jwtHelper.generateToken(user));
        return userDto;


    }

    @Override
    public UserDto signup(UserDto userDto) {

        if (!this.passwordConfiguration.confirmPasswords(userDto.getPassword(), userDto.getConfirmPassword())) {
            throw new ApplicationException("Password and confirm password does not matches!!!");
        }


        userDto.setPassword(this.passwordConfiguration.encodePassword().encode(userDto.getPassword()));
        userDto.setConfirmPassword(this.passwordConfiguration.encodePassword().encode(userDto.getConfirmPassword()));
        userDto.setUserRole(Role.USER);


        User userCreated = this.authRepo.save(this.modelMapper.map(userDto, User.class));
        UserDto userResponseDto = this.modelMapper.map(userCreated, UserDto.class);
        userResponseDto.setToken(this.jwtHelper.generateToken(userCreated));

        return userResponseDto;
    }

    @Override
    public UserDto updatePassword(UpdatePasswordDto updatePasswordDto) {


        if (!this.passwordConfiguration.confirmPasswords(updatePasswordDto.getNewPassword(), updatePasswordDto.getNewConfirmPassword())) {
            throw new ApplicationException("New password and new confirm password does not match!!");
        }


        User user = this.authRepo.findByUsername(updatePasswordDto.getUsername()).orElseThrow(() -> new ApplicationException("User not found!!"));

        user.setPassword(this.passwordConfiguration.encodePassword().encode(updatePasswordDto.getNewPassword()));
        user.setConfirmPassword(this.passwordConfiguration.encodePassword().encode(updatePasswordDto.getNewConfirmPassword()));


        this.authRepo.updatePassword(updatePasswordDto.getUsername(), user.getPassword(), user.getConfirmPassword());
        UserDto userUpdated = this.modelMapper.map(user, UserDto.class);
        userUpdated.setToken(this.jwtHelper.generateToken(user));

        return userUpdated;


    }
}
