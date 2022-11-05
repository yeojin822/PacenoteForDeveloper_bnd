package com.example.portfoliopagebuilder_bnd.builder.service;

import com.example.portfoliopagebuilder_bnd.builder.dto.Builder;
import com.example.portfoliopagebuilder_bnd.builder.dto.BuilderType;
import com.example.portfoliopagebuilder_bnd.builder.dto.DeleteInfo;
import org.springframework.http.ResponseEntity;

public interface BuilderService {
    ResponseEntity<?> detail(String id) throws Exception;
    ResponseEntity<?> save(Builder param) throws Exception;
    ResponseEntity<?> delete(DeleteInfo param);
}
