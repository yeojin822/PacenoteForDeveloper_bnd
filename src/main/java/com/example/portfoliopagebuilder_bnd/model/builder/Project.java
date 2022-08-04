package com.example.portfoliopagebuilder_bnd.model.builder;

import com.example.portfoliopagebuilder_bnd.domain.builder.Term;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Project  implements Serializable {
    static final long serialVersionUID = 1L;

    @EmbeddedId
    private BuilderId id;

    @Column(name = "text1")
    private String projectName;

    @Column(name = "text2")
    private String projectOrganigation;

    @Embedded
    private Term term;

    @Column(name = "text3")
    private String projectSkills;

    @Column(name = "text4")
    private String projectSkillSet;

    @CreationTimestamp
    private Timestamp writeDate;
    private Timestamp modifyDate;

}
