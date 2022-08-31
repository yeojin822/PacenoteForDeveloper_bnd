package com.example.portfoliopagebuilder_bnd.service.builder;

import com.example.portfoliopagebuilder_bnd.domain.BaseResponse;
import com.example.portfoliopagebuilder_bnd.domain.builder.*;
import com.example.portfoliopagebuilder_bnd.model.User;
import com.example.portfoliopagebuilder_bnd.model.builder.*;
import com.example.portfoliopagebuilder_bnd.repository.UserRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class BuilderServiceImpl implements BuilderService {

    ProjectRepository projectRepository;
    PortfolioRepository portfolioRepository;
    ProfileRepository profileRepository;
    BlockRepository blockRepository;
    CareerRepository careerRepository;
    UserRepository userRepository;

    @Autowired
    public BuilderServiceImpl(ProjectRepository projectRepository, PortfolioRepository portfolioRepository, ProfileRepository profileRepository, CareerRepository careerRepository, UserRepository userRepository,BlockRepository blockRepository){
        this.careerRepository = careerRepository;
        this.projectRepository = projectRepository;
        this.portfolioRepository = portfolioRepository;
        this.profileRepository = profileRepository;
        this.blockRepository = blockRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(Map<String, Object> param) throws Exception{
        log.info("testSave ::: {}", param);
        ObjectMapper mapper = new ObjectMapper();

        //유저 정보
        User user = userRepository.getById((String) param.get("id"));

        //데이터
        ArrayList blocks = (ArrayList) param.get("blocks");

        try {
            for (int i = 0; i < blocks.size(); i++) {
                Map<String, Object> builderItem = mapper.convertValue(blocks.get(i), Map.class);
                if (builderItem.get("blockType").equals("Profile")) {
                    Profile proile = mapper.convertValue(builderItem.get("fieldValues"), Profile.class);
                    proile.setUser(user);
                    profileRepository.save(proile);
                }

                if (builderItem.get("blockType").equals("Project")) {
                    Project project = mapper.convertValue(builderItem.get("fieldValues"), Project.class);
                    project.setUser(user);
                    projectRepository.save(project);
                }

                if (builderItem.get("blockType").equals("Career")) {
                    Career career = mapper.convertValue(builderItem.get("fieldValues"), Career.class);
                    career.setUser(user);
                    careerRepository.save(career);
                }

                if (builderItem.get("blockType").equals("Portfolio")) {
                    Portfolio portfolio = mapper.convertValue(builderItem.get("fieldValues"), Portfolio.class);
                    portfolio.setUser(user);
                    portfolioRepository.save(portfolio);
                }

            }

            //blockLayout, blockLayoutStyle set
            Block block = blockRepository.findByUserId_Id(user.getId());
            if(block != null) {
                // 저장된 block 이 있으면 update
                block.setBlockLayout(mapper.convertValue(param.get("blockLayout"), ArrayList.class));
                block.setBlockTypeStyle(mapper.convertValue(param.get("blockTypeStyle"), LinkedHashMap.class));
                blockRepository.save(block);
             }else{
                Block newBlock = new Block();
                newBlock.setUser(user);
                newBlock.setBlockLayout(mapper.convertValue(param.get("blockLayout"), ArrayList.class));
                newBlock.setBlockTypeStyle(mapper.convertValue(param.get("blockTypeStyle"), LinkedHashMap.class));
                blockRepository.save(newBlock);
            }
        }catch (Exception e){
            log.error("insert error :: {}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public ResponseEntity<?> detail(String id) throws Exception{
        BaseResponse<Builder> res = new BaseResponse();
        //공통부분
        ObjectMapper objectMapper = new ObjectMapper();
        Builder builder = new Builder();
        builder.setId(id);

        List<FieldProfile> profiles = objectMapper.convertValue(profileRepository.findAllByUserId_Id(id),new TypeReference<ArrayList<FieldProfile>>(){});
        List<FieldCareer> careers = objectMapper.convertValue(careerRepository.findAllByUserId_Id(id),new TypeReference<ArrayList<FieldCareer>>(){});
        List<FieldProject> projects = objectMapper.convertValue(projectRepository.findAllByUserId_Id(id),new TypeReference<ArrayList<FieldProject>>(){});
        List<FieldPortfolio> portfolios = objectMapper.convertValue(portfolioRepository.findAllByUserId_Id(id),new TypeReference<ArrayList<FieldPortfolio>>(){});
        Block block = blockRepository.findByUserId_Id(id);

        if(profiles.size() > 0) {
            for (int i = 0; i < profiles.size(); i++) {
                BuilderType builderType = new BuilderType();
                builderType.setBlockType("Profile");
                builderType.setFieldValues(profiles.get(i));
                builder.getBlocks().add(builderType);
            }
        }

        if(careers.size() > 0) {
            for (int i = 0; i < careers.size(); i++) {
                BuilderType builderType = new BuilderType();
                builderType.setBlockType("Career");
                builderType.setFieldValues(careers.get(i));
                builder.getBlocks().add(builderType);
            }
        }

        if(projects.size() > 0) {
            for (int i = 0; i < projects.size(); i++) {
                BuilderType builderType = new BuilderType();
                builderType.setBlockType("Project");
                builderType.setFieldValues(projects.get(i));
                builder.getBlocks().add(builderType);
            }
        }

        if(portfolios.size() > 0) {
            for (int i = 0; i < portfolios.size(); i++) {
                BuilderType builderType = new BuilderType();
                builderType.setBlockType("Portfolio");
                builderType.setFieldValues(portfolios.get(i));
                builder.getBlocks().add(builderType);
            }
        }
        if(block != null) {
            log.info("block test ::: {}", block);
           builder.setBlockLayout(objectMapper.convertValue(block.getBlockLayout(), ArrayList.class));
           builder.setBlockTypeStyle(objectMapper.convertValue(block.getBlockTypeStyle(), LinkedHashMap.class));
        }
        res.setBody(builder);

        return  new ResponseEntity(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateBuilder(Map<String, Object> param) throws Exception {
        log.info("id  ::: {}", param.get("id"));
        ArrayList blocks = (ArrayList) param.get("blocks");
        ObjectMapper mapper = new ObjectMapper();


        for (int i = 0; i < blocks.size(); i++) {
            Map<String, Object> test = mapper.convertValue(blocks.get(i), Map.class);
            System.out.println(test.get("blockType"));
            System.out.println(test);
            System.out.println("---------------------");

        }

        return null;
    }
}
