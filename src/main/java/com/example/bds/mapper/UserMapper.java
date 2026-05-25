package com.example.bds.mapper;

import com.example.bds.dto.Request.RegisterRequest;
import com.example.bds.entity.rbac.Users;
import org.mapstruct.*;


@Mapper(componentModel = "spring" , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(source = "email1" , target = "email")
    Users toEntity(RegisterRequest registerRequest);

    void update(RegisterRequest registerRequest, @MappingTarget Users users);

}
