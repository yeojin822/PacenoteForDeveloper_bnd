package com.example.portfoliopagebuilder_bnd.login.dto;

import lombok.Data;

@Data
public class PpbUser {
    private String id;
    private String username;
    private String sessionKey;
}