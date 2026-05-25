package com.example.bds.repository;

import com.example.bds.entity.listing.Category;
import com.example.bds.entity.listing.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Integer> {
    public List<Property> findByCategory(Category category);
    public Optional<Property> findById(Long id);
}
