package com.example.portfoliopagebuilder_bnd.model.builder;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    private String image1;
    private String link1;
    private String text1;
    private String text2;
    @CreationTimestamp
    private Timestamp writeDate;
    private Timestamp modifyDate;

}
