package com.example.bds.repository;

import com.example.bds.entity.listing.Listing;
import com.example.bds.entity.listing.ListingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query("""
        SELECT l FROM Listing l
        WHERE l.status = 'APPROVED'
          AND (LOWER(l.title) LIKE LOWER(CONCAT('%', :text, '%'))
               OR LOWER(l.description) LIKE LOWER(CONCAT('%', :text, '%')))
          AND (:provinceCode IS NULL OR l.provinceCode = :provinceCode)
          AND (:districtCode IS NULL OR l.districtCode = :districtCode)
          AND (:wardCode    IS NULL OR l.wardCode    = :wardCode)
        """)
    Page<Listing> searchListings(
            @Param("text")         String text,
            @Param("provinceCode") String provinceCode,
            @Param("districtCode") String districtCode,
            @Param("wardCode")     String wardCode,
            Pageable pageable
    );

    Page<Listing> findAllByStatus(ListingStatus status, Pageable pageable);
}
