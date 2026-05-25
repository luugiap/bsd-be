package com.example.bds.repository;

import com.example.bds.entity.listing.detail.HouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseDetailRepository extends JpaRepository<HouseDetail, Long> {
}
