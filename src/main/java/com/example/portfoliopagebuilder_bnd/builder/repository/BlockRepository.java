package com.example.portfoliopagebuilder_bnd.builder.repository;

import com.example.portfoliopagebuilder_bnd.builder.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, String> {
    Block findByUserId_Id(String id);
}
