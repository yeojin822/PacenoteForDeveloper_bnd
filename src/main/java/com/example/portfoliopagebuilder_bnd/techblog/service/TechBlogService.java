package com.example.portfoliopagebuilder_bnd.techblog.service;

import com.example.portfoliopagebuilder_bnd.techblog.model.dto.FavoriteDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public interface TechBlogService {
    ResponseEntity<?> list(String id) throws Exception;

    boolean save(FavoriteDto favoriteDto) throws Exception;

    boolean newList() throws Exception;

    ResponseEntity<?> saveOfficial(Map<?,?> param);

    boolean saveClickCount(Long blogId);
}
