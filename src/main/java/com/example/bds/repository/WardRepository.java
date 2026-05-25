package com.example.bds.repository;

import com.example.bds.entity.location.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward,Long> {
    Optional<Ward> findByCode(String code);
}
