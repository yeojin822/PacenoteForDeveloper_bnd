package com.example.portfoliopagebuilder_bnd.techblog.repository;

import com.example.portfoliopagebuilder_bnd.techblog.model.entity.TechFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechFavoriteRepository extends JpaRepository<TechFavorite, String> {
}
