package com.example.portfoliopagebuilder_bnd.builder.model;

import com.example.portfoliopagebuilder_bnd.login.model.User;
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
public class Portfolio implements Serializable {
    static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image1")
    private String portfolioThumbnail;

    @Column(name = "text1")
    private String portfolioName;

    @Column(name = "text2")
    private String portfolioDescription;

    @Column(name = "link1")
    private String portfolioURL;

    @Column(name = "idx")
    private String idx;

    @CreationTimestamp
    @Column(name= "write_date", nullable = false, updatable = false)
    private Timestamp writeDate;

    @UpdateTimestamp
    private Timestamp modifyDate;

}
