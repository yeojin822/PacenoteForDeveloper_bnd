package com.example.portfoliopagebuilder_bnd.builder.service;

import com.example.portfoliopagebuilder_bnd.builder.model.dto.Builder;
import com.example.portfoliopagebuilder_bnd.builder.model.dto.BuilderType;
import com.example.portfoliopagebuilder_bnd.builder.model.dto.DeleteInfo;
import com.example.portfoliopagebuilder_bnd.builder.model.dto.PortfolioInfo;
import com.example.portfoliopagebuilder_bnd.builder.model.dto.view.*;
import com.example.portfoliopagebuilder_bnd.builder.model.entity.*;
import com.example.portfoliopagebuilder_bnd.builder.repository.*;
import com.example.portfoliopagebuilder_bnd.common.BaseResponse;
import com.example.portfoliopagebuilder_bnd.login.model.User;
import com.example.portfoliopagebuilder_bnd.login.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Transactional
    public ResponseEntity<?> save(Builder param) throws Exception {
        log.info("testSave ::: {}", param);

        //유저 정보
        User user = userRepository.getById(param.getId());

        try {
            for (int i = 0; i < param.getBlocks().size(); i++) {
                BuilderType builderItem = param.getBlocks().get(i);
                String item = builderItem.getBlockType().toLowerCase();
                if (item.equals("profile")) {
                    Profile profile = mapper.convertValue(builderItem.getFieldValues(), Profile.class);
                    profile.setUser(user);
                    if (builderItem.getId() != null) {
                        profile.setId(builderItem.getId());
                    }
                    profile.setIdx(builderItem.getIdx());
                    profileRepository.save(profile);
                }

                if (item.equals("project")) {
                    Project project = mapper.convertValue(builderItem.getFieldValues(), Project.class);
                    project.setUser(user);
                    if (builderItem.getId() != null) {
                        project.setId(builderItem.getId());
                    }
                    project.setIdx(builderItem.getIdx());
                    projectRepository.save(project);
                }

                if (item.equals("career")) {
                    Career career = mapper.convertValue(builderItem.getFieldValues(), Career.class);
                    career.setUser(user);
                    if (builderItem.getId() != null) {
                        career.setId(builderItem.getId());
                    }
                    career.setIdx(builderItem.getIdx());
                    careerRepository.save(career);
                }

                if (item.equals("portfolio")) {
                    Portfolio portfolio = mapper.convertValue(builderItem.getFieldValues(), Portfolio.class);
                    portfolio.setUser(user);
                    if (builderItem.getId() != null) {
                        portfolio.setId(builderItem.getId());
                    }
                    portfolio.setIdx(builderItem.getIdx());
                    portfolioRepository.save(portfolio);
                }

                if (item.equals("markdown")) {
                    MarkDown markDown = mapper.convertValue(builderItem.getFieldValues(), MarkDown.class);
                    markDown.setUser(user);
                    if (builderItem.getId() != null) {
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
    @Transactional
    public ResponseEntity<?> delete(List<DeleteInfo> param) {
        log.info("testDelete ::: {}", param);

        try {
            for (int i = 0; i < param.size(); i++) {
                DeleteInfo deleteInfo = param.get(i);
                String item = deleteInfo.getBlockType().toLowerCase();
                if (item.equals("profile")) {
                    profileRepository.deleteById(deleteInfo.getId());
                }

                if (item.equals("project")) {
                    projectRepository.deleteById(deleteInfo.getId());
                }

                if (item.equals("career")) {
                    careerRepository.deleteById(deleteInfo.getId());
                }

                if (item.equals("portfolio")) {
                    portfolioRepository.deleteById(deleteInfo.getId());
                }

                if (item.equals("markDown")) {
                    markDownRepository.deleteById(deleteInfo.getId());
                }
            }

        } catch (Exception e) {
            log.error("delete error :: {}", e.getMessage());
            return new ResponseEntity("삭제에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPortfolio(Pageable page) {
        BaseResponse<List<PortfolioInfo>> res = new BaseResponse<>();
        res.setBody(mapper.convertValue(portfolioRepository.findAll(page).getContent(),
                new TypeReference<ArrayList<PortfolioInfo>>() {
                }));
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> detail(String id) throws Exception {
        BaseResponse<Builder> res = new BaseResponse();
        //공통부분

        Builder builder = new Builder();
        builder.setId(id);

        makeBuilderType(builder, mapper.convertValue(profileRepository.findAllByUserId_Id(id), new TypeReference<ArrayList<ProfileDto>>() {
        }));
        makeBuilderType(builder, mapper.convertValue(careerRepository.findAllByUserId_Id(id), new TypeReference<ArrayList<CareerDto>>() {
        }));
        makeBuilderType(builder, mapper.convertValue(projectRepository.findAllByUserId_Id(id), new TypeReference<ArrayList<ProjectDto>>() {
        }));
        makeBuilderType(builder, mapper.convertValue(portfolioRepository.findAllByUserId_Id(id), new TypeReference<ArrayList<PortfolioDto>>() {
        }));
        makeBuilderType(builder, mapper.convertValue(markDownRepository.findAllByUserId_Id(id), new TypeReference<ArrayList<MarkDownDto>>() {
        }));

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


    private void makeBuilderType(Builder builder, List<?> builderDataList) throws NoSuchFieldException, IllegalAccessException {
        if (builderDataList.size() == 0) {
            return;
        }

        for (Object builderData : builderDataList) {
            BuilderType builderType = new BuilderType();
            Class<?> builderClass = builderData.getClass();

            builderTypeSetting(builderData, builderType, builderClass);

            builder.getBlocks().add(builderType);
        }
    }

    private void builderTypeSetting(Object builderData, BuilderType builderType, Class<?> builderClass) throws IllegalAccessException, NoSuchFieldException {
        builderType.setBlockType(builderClass.getSimpleName().replace("Dto",""));
        builderType.setId((Long) getBuilderDataField("id", builderData, builderClass));
        builderType.setIdx((String) getBuilderDataField("idx", builderData, builderClass));
        builderType.setFieldValues(mapper.convertValue(builderData, builderClass));
    }

    private Object getBuilderDataField(String name, Object builderData, Class<?> builderClass) throws IllegalAccessException, NoSuchFieldException {
        return builderClass.getField(name).get(builderData);
    }
}
