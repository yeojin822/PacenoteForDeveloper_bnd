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
public class Profile implements Serializable {
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

    @Column(name = "image")
    private String profileImage;

    @Column(name = "text1")
    private String profileMainText;

    @Column(name = "text2")
    private String profileSubText;

    @Column(name = "applyText1")
    private String profileAdditionalInfo;

    @Column(name = "applyText2")
    private String profileApplyCompany;

    @Column(name = "applyText3")
    private String profileApplyPosition;

    @Column(name = "phone")
    private String profilePhoneNumber;

    @Column(name = "email")
    private String profileEmail;

    @Column(name = "github")
    private String profileGitHubURL;

    @Column(name = "keyword1")
    private String profileKeyword1;

    @Column(name = "keyword2")
    private String profileKeyword2;

    @Column(name = "keyword3")
    private String profileKeyword3;

    @Column(name = "keyword4")
    private String profileKeyword4;

    @Column(name = "keyword5")
    private String profileKeyword5;

    @Column(name = "idx")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String idx;

    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name= "write_date", nullable = false, updatable = false)
    private Timestamp writeDate;

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp modifyDate;
}
