package com.example.bds.repository;

import com.example.bds.entity.location.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {
    Optional<District> findByCode(String code);
}
