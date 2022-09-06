package com.example.portfoliopagebuilder_bnd.domain.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldProject {
    private String projectName;
    private String projectOrganigation;
    private String projectDescription;
    private Term projectTerm;
    private String projectSkills;
    private List<String> projectSkillSet;
}
