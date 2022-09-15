package com.example.portfoliopagebuilder_bnd.builder.service;

import com.example.portfoliopagebuilder_bnd.builder.dto.Builder;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BuilderService {
    ResponseEntity<?> detail(String id) throws Exception;
    boolean save(Builder param) throws Exception;
}
