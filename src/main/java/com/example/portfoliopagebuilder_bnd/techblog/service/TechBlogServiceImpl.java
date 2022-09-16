package com.example.portfoliopagebuilder_bnd.techblog.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TechBlogServiceImpl implements TechBlogService {



    @Override
    public boolean save(Map<String, Object> param) throws Exception{

        return true;
    }

    @Override
    public ResponseEntity<?> getList(String id) throws Exception{
        return null;
    }
}
