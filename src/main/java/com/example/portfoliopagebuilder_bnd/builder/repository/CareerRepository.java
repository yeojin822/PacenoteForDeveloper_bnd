package com.example.portfoliopagebuilder_bnd.builder.repository;

import com.example.portfoliopagebuilder_bnd.builder.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findAllByUserId_Id(String id);
}
