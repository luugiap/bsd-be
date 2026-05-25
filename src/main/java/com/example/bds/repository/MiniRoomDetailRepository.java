package com.example.bds.repository;

import com.example.bds.entity.listing.detail.MiniRoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiniRoomDetailRepository extends JpaRepository<MiniRoomDetail, Long> {
}
