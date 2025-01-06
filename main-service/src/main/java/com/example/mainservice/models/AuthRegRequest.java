package com.example.mainservice.models;

import lombok.Data;

@Data
public class AuthRegRequest {
    private String username;
    private String password;
}
