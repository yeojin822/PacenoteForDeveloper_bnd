package com.example.portfoliopagebuilder_bnd.domain.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Builder {
    private String id;
    private ArrayList<BuilderType> blocks = new ArrayList<>();
    private List<Object> blockLayout;
    private LinkedHashMap<String, Object> blockTypeStyle;
}
