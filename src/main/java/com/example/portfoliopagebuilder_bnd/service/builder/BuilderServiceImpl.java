package com.example.portfoliopagebuilder_bnd.service.builder;

import com.example.portfoliopagebuilder_bnd.domain.builder.Builder;
import com.example.portfoliopagebuilder_bnd.domain.builder.BuilderType;
import com.example.portfoliopagebuilder_bnd.domain.builder.FieldProfile;
import com.example.portfoliopagebuilder_bnd.model.User;
import com.example.portfoliopagebuilder_bnd.model.builder.Career;
import com.example.portfoliopagebuilder_bnd.model.builder.Portfolio;
import com.example.portfoliopagebuilder_bnd.model.builder.Profile;
import com.example.portfoliopagebuilder_bnd.model.builder.Project;
import com.example.portfoliopagebuilder_bnd.repository.UserRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.CareerRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.PortfolioRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.ProfileRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.ProjectRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BuilderServiceImpl implements BuilderService {

    ProjectRepository projectRepository;
    PortfolioRepository portfolioRepository;
    ProfileRepository profileRepository;
    CareerRepository careerRepository;
    UserRepository userRepository;

    @Autowired
    public BuilderServiceImpl(ProjectRepository projectRepository, PortfolioRepository portfolioRepository, ProfileRepository profileRepository, CareerRepository careerRepository, UserRepository userRepository){
        this.careerRepository = careerRepository;
        this.projectRepository = projectRepository;
        this.portfolioRepository = portfolioRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean testSave(Map<String, Object> param) throws Exception{
        log.info("testSave ::: {}", param);
        ObjectMapper mapper = new ObjectMapper();

        //유저 정보
        User user = userRepository.getById((String) param.get("id"));

        //데이터
        ArrayList blocks = (ArrayList) param.get("blocks");

        for (int i = 0; i < blocks.size(); i++) {
            Map<String, Object> builderItem = mapper.convertValue(blocks.get(i), Map.class);
            log.info("---------------------");
            if(builderItem.get("blockType").equals("Profile")) {
                Profile proile = mapper.convertValue(builderItem.get("fieldValues"), Profile.class);
                proile.setUser(user);
                log.info("set profile ::: {}", proile);
                profileRepository.save(proile);
            }

            if(builderItem.get("blockType").equals("Project")) {
                Project project = mapper.convertValue(builderItem.get("fieldValues"), Project.class);
                project.setUser(user);
                log.info("set Project ::: {}", project);
                projectRepository.save(project);
            }

            if(builderItem.get("blockType").equals("Career")) {
                Career career = mapper.convertValue(builderItem.get("fieldValues"), Career.class);
                career.setUser(user);
                log.info("set Career ::: {}", career);
                careerRepository.save(career);
            }

            if(builderItem.get("blockType").equals("Portfolio")) {
                Portfolio portfolio = mapper.convertValue(builderItem.get("fieldValues"), Portfolio.class);
                portfolio.setUser(user);
                log.info("set Portfolio ::: {}", portfolio);
                portfolioRepository.save(portfolio);
            }

        }

        return true;
    }

    @Override
    public ResponseEntity<?> detail(String id) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Builder builder = new Builder();
        builder.setId(id);

        BuilderType builderType = new BuilderType();
        log.info("id : {}", id);
        List<FieldProfile> test = objectMapper.convertValue(profileRepository.findAllByUserId_Id(id),new TypeReference<List<FieldProfile>>(){});
        log.info("test ::: {}", test );
        builderType.setBlockType(test.getClass().getTypeName());
//        builderType.setFieldValues(test);

        log.info("상태 체크 :: {}", builderType);

        return null;
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
