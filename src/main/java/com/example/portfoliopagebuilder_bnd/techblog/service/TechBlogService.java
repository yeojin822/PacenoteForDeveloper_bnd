package com.example.portfoliopagebuilder_bnd.techblog.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TechBlogService {
    ResponseEntity<?> list(String id) throws Exception;

    boolean save(String userId, String officialId) throws Exception;

    boolean newList() throws Exception;
}
