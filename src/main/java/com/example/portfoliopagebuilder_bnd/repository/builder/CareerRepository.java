package com.example.portfoliopagebuilder_bnd.repository.builder;

import com.example.portfoliopagebuilder_bnd.model.builder.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, String> {
}
