package com.example.bds.repository;

import com.example.bds.entity.listing.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attributes,Long> {
}
