package com.example.portfoliopagebuilder_bnd.builder.entity;

import com.example.portfoliopagebuilder_bnd.builder.dto.Term;
import com.example.portfoliopagebuilder_bnd.login.model.User;
import com.example.portfoliopagebuilder_bnd.common.util.StringListConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Project  implements Serializable {
    static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Long id;

    @Column(name = "idx")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String idx;

    @Column(name = "text1")
    private String projectName;

    @Column(name = "text2")
    private String projectOrganigation;

    @Column(name = "text3")
    private String projectDescription;

    @Embedded
    private Term projectTerm;

    @Column(name = "text4")
    private String projectSkills;

    @Column(name = "text5", columnDefinition = "JSON")
    @Convert(converter = StringListConverter.class)
    private List<String> projectSkillSet;

    @CreationTimestamp
    @Column(name= "write_date", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp writeDate;

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp modifyDate;

}
