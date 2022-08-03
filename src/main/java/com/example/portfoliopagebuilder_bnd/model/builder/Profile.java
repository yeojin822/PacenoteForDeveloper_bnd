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
public class Profile implements Serializable {
    static final long serialVersionUID = 1L;
    @EmbeddedId
    private BuilderId id;

    private String image;
    private String text1;
    private String text2;
    private String applyText1;
    private String applyText2;
    private String phone;
    private String email;
    private String github;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String keyword4;
    private String keyword5;
    @CreationTimestamp
    private Timestamp writeDate;
    private Timestamp modifyDate;
}
