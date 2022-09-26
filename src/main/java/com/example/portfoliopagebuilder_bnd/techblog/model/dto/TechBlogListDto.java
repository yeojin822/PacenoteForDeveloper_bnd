package com.example.portfoliopagebuilder_bnd.techblog.model.dto;

import com.example.portfoliopagebuilder_bnd.techblog.model.entity.TechFavorite;
import com.example.portfoliopagebuilder_bnd.techblog.model.entity.TechOfficial;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class TechBlogListDto {
    TechFavorite techFavorite;
    List<TechOfficial> techOfficialList;
}
