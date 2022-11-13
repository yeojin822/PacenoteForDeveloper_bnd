package com.example.portfoliopagebuilder_bnd.builder.model.entity;

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
    private User user;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "idx")
    public String idx;

    @Column(name = "text1")
    private String markdownText;

    @CreationTimestamp
    @Column(name= "write_date", nullable = false, updatable = false)
    private Timestamp writeDate;

    @UpdateTimestamp
    private Timestamp modifyDate;
}
