package com.backend.blog.service;

import com.backend.blog.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserId(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

}

