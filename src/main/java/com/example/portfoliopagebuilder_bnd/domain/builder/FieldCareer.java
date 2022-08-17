package com.example.portfoliopagebuilder_bnd.domain.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldCareer {
    private Long idx;
    private String careerMainText;
    private String careerSubText;
    private String careerDescription;
    private Term careerTerm;
}
