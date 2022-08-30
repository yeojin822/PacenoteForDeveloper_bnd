package com.example.portfoliopagebuilder_bnd.domain.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldPortfolio {
    private Long id;
    private String block_idx;
    private String portfolioThumbnail;
    private String portfolioName;
    private String portfolioDescription;
    private String portfolioURL;
}
