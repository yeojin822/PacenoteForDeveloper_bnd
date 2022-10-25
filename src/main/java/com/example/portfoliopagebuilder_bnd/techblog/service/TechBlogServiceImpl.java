package com.example.portfoliopagebuilder_bnd.techblog.service;

import com.example.portfoliopagebuilder_bnd.common.BaseResponse;
import com.example.portfoliopagebuilder_bnd.common.util.web.WebRequestUtil;
import com.example.portfoliopagebuilder_bnd.techblog.model.dto.TechBlogListDto;
import com.example.portfoliopagebuilder_bnd.techblog.model.entity.TechFavorite;
import com.example.portfoliopagebuilder_bnd.techblog.model.entity.TechOfficial;
import com.example.portfoliopagebuilder_bnd.techblog.repository.TechFavoriteRepository;
import com.example.portfoliopagebuilder_bnd.techblog.repository.TechOfficialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TechBlogServiceImpl implements TechBlogService {

    private final TechOfficialRepository techOfficialRepository;
    private final TechFavoriteRepository techFavoriteRepository;

    @Override
    public boolean save(String userId, String officialId) throws Exception {
        TechFavorite techFavorite = techFavoriteRepository.findById(userId).orElse(null);

        if (techFavorite == null) {
            techFavorite = new TechFavorite();
        }

        List<String> blogList = techFavorite.getBlogId();

        blogList.add(officialId);

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

        param.entrySet().stream().map(entry -> entry.getValue());

        techOfficialRepository.saveAll(officials);

        response.setBody("complete insert");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<TechOfficial> officialList() throws Exception {
        return techOfficialRepository.findAll();
    }

    private TechFavorite userFavorite(String id) throws Exception {
        return techFavoriteRepository.findById(id).orElse(null);
    }
}
