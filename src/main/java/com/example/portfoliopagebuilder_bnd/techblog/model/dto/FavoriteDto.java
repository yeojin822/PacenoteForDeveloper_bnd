package com.example.portfoliopagebuilder_bnd.techblog.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class FavoriteDto {

    private String userId;

    private Set<String> blogIds;
}
