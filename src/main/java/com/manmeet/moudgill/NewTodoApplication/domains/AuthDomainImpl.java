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
    public AuthDomainImpl(
            PasswordConfiguration passwordConfiguration,
            AuthRepo authRepo,
            ModelMapper modelMapper,
            JwtHelper jwtHelper
    ) {
        this.passwordConfiguration = passwordConfiguration;
        this.authRepo = authRepo;
        this.modelMapper = modelMapper;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public UserDto login(LoginDto loginDto) {
        User user = authRepo.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ApplicationException("User not found!!"));

        if (!passwordConfiguration.verifyHashedPassword(loginDto.getPassword(), user.getPassword())) {
            throw new ApplicationException("Incorrect Credentials!!");
        }

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setToken(jwtHelper.generateToken(user));
        return userDto;
    }

    @Override
    public UserDto signup(UserDto userDto) {
        validatePasswords(userDto.getPassword(), userDto.getConfirmPassword());
        encodePasswords(userDto);
        setDefaultUserRole(userDto);

        User userCreated = authRepo.save(modelMapper.map(userDto, User.class));

        return mapToUserDtoWithToken(userCreated);
    }

    @Override
    public UserDto updatePassword(UpdatePasswordDto updatePasswordDto) {
        validatePasswords(updatePasswordDto.getNewPassword(), updatePasswordDto.getNewConfirmPassword());
        User user = findUserByUsername(updatePasswordDto.getUsername());
        updatePasswordAndConfirmPassword(user, updatePasswordDto);

        authRepo.updatePassword(updatePasswordDto.getUsername(), user.getPassword(), user.getConfirmPassword());

        return mapToUserDtoWithToken(user);
    }

    private void validatePasswords(String password, String confirmPassword) {
        if (!passwordConfiguration.confirmPasswords(password, confirmPassword)) {
            throw new ApplicationException("Password and confirm password do not match!!!");
        }
    }

    private void encodePasswords(UserDto userDto) {
        userDto.setPassword(passwordConfiguration.encodePassword().encode(userDto.getPassword()));
        userDto.setConfirmPassword(passwordConfiguration.encodePassword().encode(userDto.getConfirmPassword()));
    }

    private void setDefaultUserRole(UserDto userDto) {
        userDto.setUserRole(Role.USER);
    }

    private UserDto mapToUserDtoWithToken(User user) {
        UserDto userResponseDto = modelMapper.map(user, UserDto.class);
        userResponseDto.setToken(jwtHelper.generateToken(user));
        return userResponseDto;
    }

    private User findUserByUsername(String username) {
        return authRepo.findByUsername(username).orElseThrow(() -> new ApplicationException("User not found!!"));
    }

    private void updatePasswordAndConfirmPassword(User user, UpdatePasswordDto updatePasswordDto) {
        user.setPassword(passwordConfiguration.encodePassword().encode(updatePasswordDto.getNewPassword()));
        user.setConfirmPassword(passwordConfiguration.encodePassword().encode(updatePasswordDto.getNewConfirmPassword()));
    }
}
