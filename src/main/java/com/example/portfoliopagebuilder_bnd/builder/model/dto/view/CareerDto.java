package com.example.portfoliopagebuilder_bnd.builder.model.dto.view;

import com.example.portfoliopagebuilder_bnd.builder.model.dto.Term;
import com.example.portfoliopagebuilder_bnd.login.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class CareerDto implements Serializable {
    static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String idx;

    private String careerMainText;

    private String careerSubText;

    private String careerDescription;

    private Term careerTerm;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp writeDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp modifyDate;
}
