package com.example.portfoliopagebuilder_bnd.domain.builder;

import com.example.portfoliopagebuilder_bnd.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldProfile {
    private Long id;
    private String block_idx;
    private String profileImage;
    private String profileMainText;
    private String profileSubText;
    private String profileAdditionalInfo;
    private String profileApplyCompany;
    private String profileApplyPosition;
    private String profilePhoneNumber;
    private String profileEmail;
    private String profileGitHubURL;
    private String profileKeyword1;
    private String profileKeyword2;
    private String profileKeyword3;
    private String profileKeyword4;
    private String profileKeyword5;
}
