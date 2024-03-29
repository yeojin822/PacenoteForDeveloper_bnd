package com.example.portfoliopagebuilder_bnd.builder.repository;

import com.example.portfoliopagebuilder_bnd.builder.model.entity.MarkDown;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkDownRepository extends JpaRepository<MarkDown, Long> {
    List<MarkDown> findAllByUserId_Id(String id);
}
