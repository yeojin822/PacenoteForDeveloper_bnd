package com.example.portfoliopagebuilder_bnd.builder.service;

import com.example.portfoliopagebuilder_bnd.builder.model.dto.Builder;
import com.example.portfoliopagebuilder_bnd.builder.model.dto.DeleteInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BuilderService {
    ResponseEntity<?> detail(String id) throws Exception;
    ResponseEntity<?> save(Builder param) throws Exception;
    ResponseEntity<?> delete(List<DeleteInfo> param);
    ResponseEntity<?> getPortfolio(Pageable page);
}
