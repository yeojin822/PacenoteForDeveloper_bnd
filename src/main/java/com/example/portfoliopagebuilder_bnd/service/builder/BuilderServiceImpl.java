package com.example.portfoliopagebuilder_bnd.service.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BuilderServiceImpl implements BuilderService {

    @Override
    public ResponseEntity<?> updateBuilder(Map<String, Object> param) throws Exception {
        log.info("id  ::: {}", param.get("id"));
        ArrayList blocks = (ArrayList) param.get("blocks");
        ObjectMapper mapper = new ObjectMapper();

        for (int i = 0; i < blocks.size(); i++) {
            Map<String, Object> test = mapper.convertValue(blocks.get(i), Map.class);
            System.out.println(test.get("blockType"));
            System.out.println("---------------------");

        }

        return null;
    }
}
