package com.example.bds.repository;


import com.example.bds.entity.listing.AttributeDefinition;
import com.example.bds.entity.listing.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AttributeDefinitionRepository extends JpaRepository<AttributeDefinition,Long> {

    Optional<List<AttributeDefinition>>findByProperty_Id(Long id);


    @Query("""
    SELECT ad.attribute FROM AttributeDefinition ad WHERE ad.property.id = :propertyId
"""
    )
     Optional<List<Attributes>> findByProperty(@Param("propertyId") Long propertyId);
}
