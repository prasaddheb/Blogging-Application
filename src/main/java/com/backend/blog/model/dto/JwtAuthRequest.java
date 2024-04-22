package com.backend.blog.model.dto;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private  String username;

    private  String password;
}
