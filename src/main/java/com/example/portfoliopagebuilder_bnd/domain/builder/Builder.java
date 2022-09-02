package com.example.portfoliopagebuilder_bnd.domain.builder;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Builder {
    private String id;
    private ArrayList<BuilderType> blocks = new ArrayList<>();
    private List<Object> blockLayout;
    private LinkedHashMap<String, Object> blockTypeStyle;
}
