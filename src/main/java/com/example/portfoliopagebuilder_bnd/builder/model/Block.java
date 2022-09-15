package com.example.portfoliopagebuilder_bnd.builder.model;

import com.example.portfoliopagebuilder_bnd.login.model.User;
import com.example.portfoliopagebuilder_bnd.common.util.StringListConverter;
import com.example.portfoliopagebuilder_bnd.common.util.StringMapConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Block {
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "block_layout", columnDefinition = "JSON")
    @Convert(converter = StringListConverter.class)
    private List<Object> blockLayout;

    @Column(name = "block_style",columnDefinition = "JSON")
    @Convert(converter = StringMapConverter.class)
    private LinkedHashMap<String,Object> blockTypeStyle;

    @CreationTimestamp
    @Column(name= "write_date", nullable = false, updatable = false)
    private Timestamp writeDate;
}
