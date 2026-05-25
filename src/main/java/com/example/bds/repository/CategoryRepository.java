package com.example.bds.repository;

import com.example.bds.entity.listing.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    public Optional<Category> findById(Long id);
}
