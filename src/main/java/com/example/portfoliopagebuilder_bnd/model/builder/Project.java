package com.example.portfoliopagebuilder_bnd.model.builder;

import com.example.portfoliopagebuilder_bnd.domain.builder.Term;
import com.example.portfoliopagebuilder_bnd.model.User;
import com.example.portfoliopagebuilder_bnd.util.StringListConverter;
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
    private User user;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "idx")
    private String idx;

    @CreationTimestamp
    @Column(name= "write_date", nullable = false, updatable = false)
    private Timestamp writeDate;

    @UpdateTimestamp
    private Timestamp modifyDate;

}
