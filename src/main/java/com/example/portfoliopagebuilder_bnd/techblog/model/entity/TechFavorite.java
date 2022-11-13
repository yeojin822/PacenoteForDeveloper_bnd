package com.example.portfoliopagebuilder_bnd.techblog.model.entity;

import com.example.portfoliopagebuilder_bnd.common.util.StringListConverter;
import com.example.portfoliopagebuilder_bnd.common.util.StringMapConverter;
import com.example.portfoliopagebuilder_bnd.common.util.StringSetConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class TechFavorite implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "blog_id",columnDefinition = "JSON")
    @Convert(converter = StringSetConverter.class)
    private Set<String> blogId;

    @Column(name = "write_date")
    private Timestamp  writeDate;

    @Column(name = "modify_date")
    private Timestamp modifyDate;
}
