package com.example.portfoliopagebuilder_bnd.oauth.exception;

public enum ExceptionEnum {
    SignatureException("SignatureException","유효하지 않은 JWT 서명"),
    MalformedJwtException("MalformedJwtException","유효하지 않은 JWT"),
    ExpiredJwtException("ExpiredJwtException","만료된 JWT"),
    UnsupportedJwtException("UnsupportedJwtException","지원하지 않는 JWT"),
    IllegalArgumentException("IllegalArgumentException","빈값")
    ;

    private String type;
    private String msg;

    ExceptionEnum(String type, String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

}



