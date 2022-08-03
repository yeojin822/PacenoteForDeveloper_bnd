package com.example.portfoliopagebuilder_bnd.domain.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class Token {
    String sessionKey;

    public Token(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
