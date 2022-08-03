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
public class Project  implements Serializable {
    static final long serialVersionUID = 1L;

    @EmbeddedId
    private BuilderId id;
    private String text1;
    private String text2;
    private String startDate;
    private String endDate;
    private String text3;
    private String text4;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    @CreationTimestamp
    private Timestamp writeDate;
    private Timestamp modifyDate;

}
