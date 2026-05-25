package com.example.bds.repository;

import com.example.bds.entity.listing.detail.ApartmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentDetailRepository extends JpaRepository<ApartmentDetail, Long> {
}
