package com.example.portfoliopagebuilder_bnd.model.builder;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class BuilderId implements Serializable {

    @Column(name = "id")
    private String id;

    @Column(name = "idx")
    private Long idx;
}
