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
public class Career implements Serializable {
    static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "text1")
    private String careerMainText;

    @Column(name = "text2")
    private String careerSubText;

    @Column(name = "text3")
    private String careerDescription;

    @Embedded
    private Term careerTerm;

    @CreationTimestamp
    private Timestamp writeDate;
    private Timestamp modifyDate;
}
