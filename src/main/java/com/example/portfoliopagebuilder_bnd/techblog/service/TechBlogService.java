package com.example.portfoliopagebuilder_bnd.techblog.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TechBlogService {
    ResponseEntity<?> detail(String id) throws Exception;
    boolean save(Map<String, Object> param) throws Exception;
}
