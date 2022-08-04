package com.example.portfoliopagebuilder_bnd.model.builder;

import com.example.portfoliopagebuilder_bnd.domain.builder.Term;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Career implements Serializable {
    static final long serialVersionUID = 1L;

    @EmbeddedId
    private BuilderId id;

    @Column(name = "text1")
    private String careerMainText;

    @Column(name = "text2")
    private String careerSubText;

    @Column(name = "text3")
    private String careerDescription;

    @Embedded
    private Term term;

    @CreationTimestamp
    private Timestamp writeDate;
    private Timestamp modifyDate;
}
