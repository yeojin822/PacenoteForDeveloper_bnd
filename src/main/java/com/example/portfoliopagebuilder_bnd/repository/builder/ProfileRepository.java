package com.example.portfoliopagebuilder_bnd.repository.builder;

import com.example.portfoliopagebuilder_bnd.model.builder.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    List<Object> findAllByUserId_Id(String id);
}
