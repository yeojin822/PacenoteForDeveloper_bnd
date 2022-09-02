package com.example.portfoliopagebuilder_bnd.service.builder;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BuilderService {
    ResponseEntity<?> detail(String id) throws Exception;
    boolean save(Map<String, Object> param) throws Exception;
}
