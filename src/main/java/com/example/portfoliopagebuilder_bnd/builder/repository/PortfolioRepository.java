package com.example.portfoliopagebuilder_bnd.builder.repository;

import com.example.portfoliopagebuilder_bnd.builder.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByUserId_Id(String id);
    Page<Portfolio> findAll(Pageable pageable);
}
