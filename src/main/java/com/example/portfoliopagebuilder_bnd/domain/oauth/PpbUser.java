package com.example.portfoliopagebuilder_bnd.domain.oauth;

import lombok.Data;

@Data
public class PpbUser {
    private String id;
    private String username;
    private String sessionKey;
}
