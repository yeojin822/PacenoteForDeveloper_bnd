package com.example.portfoliopagebuilder_bnd.builder.service;

import com.example.portfoliopagebuilder_bnd.builder.dto.Builder;
import com.example.portfoliopagebuilder_bnd.builder.dto.BuilderType;
import com.example.portfoliopagebuilder_bnd.builder.model.*;
import com.example.portfoliopagebuilder_bnd.builder.repository.*;
import com.example.portfoliopagebuilder_bnd.common.BaseResponse;
import com.example.portfoliopagebuilder_bnd.login.model.User;
import com.example.portfoliopagebuilder_bnd.login.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BuilderServiceImpl implements BuilderService {

    private final ProjectRepository projectRepository;
    private final PortfolioRepository portfolioRepository;
    private final ProfileRepository profileRepository;
    private final BlockRepository blockRepository;
    private final CareerRepository careerRepository;
    private final MarkDownRepository markDownRepository;
    private final UserRepository userRepository;


    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public ResponseEntity<?> save(Builder param) throws Exception {
        log.info("testSave ::: {}", param);

        //유저 정보
        User user = userRepository.getById(param.getId());

        try {
            for (int i = 0; i < param.getBlocks().size(); i++) {
                BuilderType builderItem = param.getBlocks().get(i);
                log.info("builderItem : {}", builderItem);
                if (builderItem.getBlockType().equals("Profile")) {
                    Profile profile = mapper.convertValue(builderItem.getFieldValues(), Profile.class);
                    profile.setUser(user);
                    if(builderItem.getId() != null){
                        profile.setId(builderItem.getId());
                    }
                    profile.setIdx(builderItem.getIdx());
                    profileRepository.save(profile);
                }

                if (builderItem.getBlockType().equals("Project")) {
                    Project project = mapper.convertValue(builderItem.getFieldValues(), Project.class);
                    project.setUser(user);
                    if(builderItem.getId() != null){
                        project.setId(builderItem.getId());
                    }
                    project.setIdx(builderItem.getIdx());
                    projectRepository.save(project);
                }

                if (builderItem.getBlockType().equals("Career")) {
                    Career career = mapper.convertValue(builderItem.getFieldValues(), Career.class);
                    career.setUser(user);
                    if(builderItem.getId() != null){
                        career.setId(builderItem.getId());
                    }
                    career.setIdx(builderItem.getIdx());
                    careerRepository.save(career);
                }

                if (builderItem.getBlockType().equals("Portfolio")) {
                    Portfolio portfolio = mapper.convertValue(builderItem.getFieldValues(), Portfolio.class);
                    portfolio.setUser(user);
                    if(builderItem.getId() != null){
                        portfolio.setId(builderItem.getId());
                    }
                    portfolio.setIdx(builderItem.getIdx());
                    portfolioRepository.save(portfolio);
                }

                if (builderItem.getBlockType().equals("MarkDown")) {
                    MarkDown markDown = mapper.convertValue(builderItem.getFieldValues(), MarkDown.class);
                    markDown.setUser(user);
                    if(builderItem.getId() != null){
                        markDown.setId(builderItem.getId());
                    }
                    markDown.setIdx(builderItem.getIdx());
                    markDownRepository.save(markDown);
                }
            }

            //blockLayout, blockLayoutStyle set
            Block block = blockRepository.findByUserId_Id(user.getId());
            if (block != null) {
                // 저장된 block 이 있으면 update
                block.setBlockLayout(param.getBlockLayout());
                block.setBlockTypeStyle(param.getBlockTypeStyle());
                blockRepository.save(block);
            } else {
                Block newBlock = new Block();
                newBlock.setUser(user);
                newBlock.setBlockLayout(param.getBlockLayout());
                newBlock.setBlockTypeStyle(param.getBlockTypeStyle());
                blockRepository.save(newBlock);
            }
        } catch (Exception e) {
            log.error("insert error :: {}", e.getMessage());
            return new ResponseEntity("저장에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> detail(String id) throws Exception {
        BaseResponse<Builder> res = new BaseResponse();
        //공통부분

        Builder builder = new Builder();
        builder.setId(id);

        makeBuilderType(builder, profileRepository.findAllByUserId_Id(id));
        makeBuilderType(builder, careerRepository.findAllByUserId_Id(id));
        makeBuilderType(builder, projectRepository.findAllByUserId_Id(id));
        makeBuilderType(builder, portfolioRepository.findAllByUserId_Id(id));
        makeBuilderType(builder, markDownRepository.findAllByUserId_Id(id));

        Block block = blockRepository.findByUserId_Id(id);
        if (block != null) {
            log.info("block test ::: {}", block);
            builder.setBlockLayout(mapper.convertValue(block.getBlockLayout(), ArrayList.class));
            builder.setBlockTypeStyle(mapper.convertValue(block.getBlockTypeStyle(), LinkedHashMap.class));
        }
        System.out.println(builder);
        res.setBody(builder);

        return new ResponseEntity(res, HttpStatus.OK);
    }


    private void makeBuilderType(Builder builder, List<?> builderData) throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
        if (builderData.size() > 0) {
            for (int i = 0; i < builderData.size(); i++) {
                BuilderType builderType = new BuilderType();
                Class<?> builderClass = builderData.get(i).getClass();
                builderType.setBlockType(builderClass.getSimpleName());
                builderType.setId((Long) builderClass.getField("id").get(builderData.get(i)));
                builderType.setIdx((String) builderClass.getField("idx").get(builderData.get(i)));
                builderType.setFieldValues(mapper.convertValue(builderData.get(i), builderClass));
                builder.getBlocks().add(builderType);
            }
        }
    }
}
