package com.example.portfoliopagebuilder_bnd.service.builder;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BuilderService {
    ResponseEntity<?> updateBuilder(Map<String, Object> param) throws Exception;
}
