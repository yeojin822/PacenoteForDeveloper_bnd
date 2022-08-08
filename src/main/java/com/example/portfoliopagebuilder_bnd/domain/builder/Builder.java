package com.example.portfoliopagebuilder_bnd.domain.builder;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Builder {
    private String id;
    private ArrayList<BuilderType> blocks;
}
