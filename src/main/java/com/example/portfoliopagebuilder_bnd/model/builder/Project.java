package com.example.portfoliopagebuilder_bnd.model.builder;

import com.example.portfoliopagebuilder_bnd.domain.builder.Term;
import com.example.portfoliopagebuilder_bnd.model.User;
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

    @ManyToOne
    @JoinColumn(name="id")
    private User user;

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "text1")
    private String projectName;

    @Column(name = "text2")
    private String projectDescription;

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
