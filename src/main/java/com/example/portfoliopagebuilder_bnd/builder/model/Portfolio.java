package com.example.portfoliopagebuilder_bnd.builder.model;

import com.example.portfoliopagebuilder_bnd.login.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "image1")
    private String portfolioThumbnail;

    @Column(name = "text1")
    private String portfolioName;

    @Column(name = "text2")
    private String portfolioDescription;

    @Column(name = "link1")
    private String portfolioURL;

    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name= "write_date", nullable = false, updatable = false)
    private Timestamp writeDate;

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp modifyDate;

}
