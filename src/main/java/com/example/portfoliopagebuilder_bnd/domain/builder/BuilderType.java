package com.example.portfoliopagebuilder_bnd.domain.builder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuilderType<T> {
    private String blockType;
    private Long id;
    private String idx;
    private T fieldValues;
}
