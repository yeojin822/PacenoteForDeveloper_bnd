package com.example.portfoliopagebuilder_bnd.model.builder;

import com.example.portfoliopagebuilder_bnd.domain.builder.Term;
import com.example.portfoliopagebuilder_bnd.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text1")
    private String careerMainText;

    @Column(name = "text2")
    private String careerSubText;

    @Column(name = "text3")
    private String careerDescription;

    @Embedded
    private Term careerTerm;

    @Column(name = "block_idx")
    private String blockIdx;

    @CreationTimestamp
    @Column(name= "write_date", nullable = false, updatable = false)
    private Timestamp writeDate;
    @UpdateTimestamp
    private Timestamp modifyDate;
}
