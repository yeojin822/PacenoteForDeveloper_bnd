package com.example.portfoliopagebuilder_bnd.builder.repository;

import com.example.portfoliopagebuilder_bnd.builder.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    List<Object> findAllByUserId_Id(String id);
}
