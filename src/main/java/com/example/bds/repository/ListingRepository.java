package com.example.bds.repository;

import com.example.bds.dto.Response.Listing.ListingResponse;
import com.example.bds.entity.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

}
