package com.example.portfoliopagebuilder_bnd.builder.model;

import com.example.portfoliopagebuilder_bnd.builder.dto.Term;
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
public class MarkDown implements Serializable {
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

    @Column(name = "text1")
    private String markdownText;

    @CreationTimestamp
    @Column(name= "write_date", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp writeDate;

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp modifyDate;
}
