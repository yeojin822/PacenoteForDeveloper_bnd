package com.example.portfoliopagebuilder_bnd.builder.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class Term {

    @Column(name = "strDate")
    private String from;

    @Column(name = "endDate")
    private String to;
}
