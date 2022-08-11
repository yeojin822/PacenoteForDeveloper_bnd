package com.example.portfoliopagebuilder_bnd.domain.builder;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BuilderType {
    private String blockType;
    private List<Object> fieldValues = new ArrayList<>();
}
