package com.backend.blog.controller;

import com.backend.blog.model.dto.ApiResponse;
import com.backend.blog.model.dto.UserDto;
import com.backend.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto createdUserDto = userService.createUser(userDto);

        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUserDto);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted SuccessFully", Boolean.TRUE), HttpStatus.OK);
    }

    @GetMapping
    public  ResponseEntity<List<UserDto>> getAllUsers(){
        return  ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/{userId}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId){
        return  ResponseEntity.ok(userService.getUserId(userId));
    }
}
