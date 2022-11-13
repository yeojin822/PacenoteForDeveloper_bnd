package com.example.portfoliopagebuilder_bnd.techblog.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class TechOfficial implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @Column(name = "blog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;

    @Column(name = "logo")
    private String logo;

    @Column(name = "official_name")
    private String officialName;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "link")
    private String link;

    @Column(name = "thevc_link")
    private String thevcLink;

    @Column(name = "youtube_link")
    private String youtubeLink;

    @Column(name = "click_count")
    private int clickCount;

    @Column(name = "favorite_count")
    private int favoriteCount;
    
    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "write_date")
    private Timestamp writeDate;
}
