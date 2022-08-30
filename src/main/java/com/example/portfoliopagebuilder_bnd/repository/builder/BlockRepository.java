package com.example.portfoliopagebuilder_bnd.repository.builder;

import com.example.portfoliopagebuilder_bnd.model.builder.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, String> {
    Block findByUserId_Id(String id);
}
