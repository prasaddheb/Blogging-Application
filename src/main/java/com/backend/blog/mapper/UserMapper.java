package com.backend.blog.mapper;

import com.backend.blog.entity.User;
import com.backend.blog.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
