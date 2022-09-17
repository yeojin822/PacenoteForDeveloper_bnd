package com.example.portfoliopagebuilder_bnd.techblog.service;


import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import com.example.portfoliopagebuilder_bnd.common.util.web.WebRequestUtil;
import com.example.portfoliopagebuilder_bnd.techblog.repository.TechFavoriteRepository;
import com.example.portfoliopagebuilder_bnd.techblog.repository.TechOfficialRepository;
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

    private final TechOfficialRepository techOfficialRepository;
    private final TechFavoriteRepository techFavoriteRepository;



    @Override
    public boolean save(Map<String, Object> param) throws Exception{

        return true;
    }

    @Override
    public ResponseEntity<?> getTechBlogList(String id) throws  Exception{

        this.getOfficialList();
        this.getFavoriteByUser(id);
        return null;
    }

    public Map<?,?> getNewestTechBlogPost() throws  Exception{
        String url = "http://localhost:8000/tech-blog/datas";

        String response = WebRequestUtil.httpRequest(url, "", "GET");

        // DB저장
        // 스케줄러에 성공여부 송신

        return null;
    }

    private ResponseEntity<?> getOfficialList() throws Exception{
        return null;
    }


    private ResponseEntity<?> getFavoriteByUser(String id) throws Exception{
        return null;
    }
}
