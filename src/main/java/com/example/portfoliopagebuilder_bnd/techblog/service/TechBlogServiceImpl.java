package com.example.portfoliopagebuilder_bnd.techblog.service;

import com.example.portfoliopagebuilder_bnd.common.BaseResponse;
import com.example.portfoliopagebuilder_bnd.common.util.web.WebRequestUtil;
import com.example.portfoliopagebuilder_bnd.techblog.model.dto.FavoriteDto;
import com.example.portfoliopagebuilder_bnd.techblog.model.dto.TechBlogListDto;
import com.example.portfoliopagebuilder_bnd.techblog.model.entity.TechFavorite;
import com.example.portfoliopagebuilder_bnd.techblog.model.entity.TechOfficial;
import com.example.portfoliopagebuilder_bnd.techblog.repository.TechFavoriteRepository;
import com.example.portfoliopagebuilder_bnd.techblog.repository.TechOfficialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TechBlogServiceImpl implements TechBlogService {

    private final TechOfficialRepository techOfficialRepository;
    private final TechFavoriteRepository techFavoriteRepository;

    @Override
    public boolean save(FavoriteDto favoriteDto) throws Exception {
        TechFavorite techFavorite = techFavoriteRepository.findById(favoriteDto.getUserId()).orElse(null);

        if (techFavorite == null) {
            techFavorite = new TechFavorite();
            techFavorite.setUser_id(favoriteDto.getUserId());
            techFavorite.setWriteDate(Timestamp.valueOf(LocalDateTime.now()));
        }

        Set<String> blogList = techFavorite.getBlogId();

        if(blogList == null){
            blogList = new HashSet<>();
        }

        blogList.addAll(favoriteDto.getBlogIds());
        techFavorite.setModifyDate(Timestamp.valueOf(LocalDateTime.now()));
        techFavorite.setBlogId(blogList);

        techFavoriteRepository.save(techFavorite);

        return true;
    }

    @Override
    public ResponseEntity<?> list(String id) throws Exception {
        BaseResponse<TechBlogListDto> response = new BaseResponse();
        TechBlogListDto techBlogListDto = new TechBlogListDto();
        techBlogDtoSetting(id, techBlogListDto);

        response.setBody(techBlogListDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void techBlogDtoSetting(String id, TechBlogListDto techBlogListDto) throws Exception {
        techBlogListDto.setTechFavorite(this.userFavorite(id));
        techBlogListDto.setTechOfficialList(this.officialList());
    }

    public boolean newList() throws Exception {
        String url = "http://localhost:8000/tech_blog/datas";
        Map<String, String> map = null;

        try {
            String response = WebRequestUtil.httpRequest(url, "", "GET");
            map = new ObjectMapper().readValue(response, Map.class);

            List<TechOfficial> techOfficials = techOfficialRepository.findAll();

            for (TechOfficial techOfficial : techOfficials) {
                String officialName = techOfficial.getOfficialName();
                Timestamp newTime = Timestamp.valueOf(map.get(officialName));

                if(!map.containsKey(officialName) || techOfficial.getUpdateDate() == newTime){
                    continue;
                }

                techOfficial.setUpdateDate(newTime);
            }

            techOfficialRepository.saveAll(techOfficials);
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public ResponseEntity<?> saveOfficial(Map<?, ?> param) {
        BaseResponse<String> response = new BaseResponse();
        List<TechOfficial> officials = new ArrayList<>();

        //아래 map을 officials에 저장해야함
        param.entrySet().stream().map(entry -> entry.getValue());
        
        techOfficialRepository.saveAll(officials);

        response.setBody("complete insert");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public boolean saveClickCount(Long blogId) {
        TechOfficial techOfficial = techOfficialRepository.findById(blogId).orElseThrow();
        techOfficial.setClickCount(techOfficial.getClickCount()+1);

        techOfficialRepository.save(techOfficial);

        return true;
    }

    private List<TechOfficial> officialList() throws Exception {
        return techOfficialRepository.findAll();
    }

    private TechFavorite userFavorite(String id) throws Exception {
        return techFavoriteRepository.findById(id).orElse(null);
    }
}
