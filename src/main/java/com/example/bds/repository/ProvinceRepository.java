package com.example.bds.repository;

import com.example.bds.entity.location.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province,Long> {
    Optional<Province> findByCode(String code);
}
