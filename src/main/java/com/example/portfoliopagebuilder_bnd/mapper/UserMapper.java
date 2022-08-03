package com.example.portfoliopagebuilder_bnd.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    String getProfilePath (String id);
}
