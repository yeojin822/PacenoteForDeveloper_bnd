package com.example.portfoliopagebuilder_bnd.repository.builder;

import com.example.portfoliopagebuilder_bnd.model.builder.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    List<Object> findAllByUserId_Id(String id);
}
