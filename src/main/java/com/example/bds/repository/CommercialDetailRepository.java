package com.example.bds.repository;

import com.example.bds.entity.listing.detail.CommercialDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialDetailRepository extends JpaRepository<CommercialDetail, Long> {
}
