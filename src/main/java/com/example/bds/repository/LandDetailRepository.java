package com.example.bds.repository;

import com.example.bds.entity.listing.detail.LandDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandDetailRepository extends JpaRepository<LandDetail, Long> {
}
