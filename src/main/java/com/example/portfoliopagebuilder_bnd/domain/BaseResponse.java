package com.example.portfoliopagebuilder_bnd.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@JsonPropertyOrder()
public class BaseResponse<T>{

    @JsonProperty("statusCode")
    private int statusCode = 200;

    @JsonProperty("body")
    private T body;

    public BaseResponse(T body){
        this.body = body;
    }

}
