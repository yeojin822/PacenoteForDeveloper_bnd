package com.example.portfoliopagebuilder_bnd.techblog.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class TechOfficialEntity {

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

    @Column(name = "update_date")
    private DateTime updateDate;

    @Column(name = "write_date")
    private DateTime writeDate;

    @Column(name = "modify_date")
    private DateTime modifyDate;

}
