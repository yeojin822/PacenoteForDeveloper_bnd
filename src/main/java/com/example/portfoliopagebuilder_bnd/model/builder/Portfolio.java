package com.example.portfoliopagebuilder_bnd.model.builder;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Portfolio implements Serializable {
    static final long serialVersionUID = 1L;
    @EmbeddedId
    private BuilderId id;

    @Column(name = "image1")
    private String portfolioThumbnail;

    @Column(name = "text1")
    private String portfolioName;

    @Column(name = "text2")
    private String portfolioDescription;

    @Column(name = "link1")
    private String portfolioURL;

    @CreationTimestamp
    private Timestamp writeDate;

    private Timestamp modifyDate;

}