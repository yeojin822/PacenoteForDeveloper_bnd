package com.example.portfoliopagebuilder_bnd.builder.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class PortfolioInfo implements Serializable {
    static final long serialVersionUID = 1L;
    public Long id;
    public String idx;
    private String portfolioThumbnail;
    private String portfolioName;
    private String portfolioDescription;
    private String portfolioURL;
    private Timestamp writeDate;
    private Timestamp modifyDate;

}
