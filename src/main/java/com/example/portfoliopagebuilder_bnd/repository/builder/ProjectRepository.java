package com.example.portfoliopagebuilder_bnd.repository.builder;

import com.example.portfoliopagebuilder_bnd.model.builder.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, String> {
    List<Object> findAllByUserId_Id(String id);
}
