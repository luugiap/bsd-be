package com.example.bds.entity.listing.detail;

import com.example.bds.entity.BasedEntity;
import com.example.bds.entity.listing.BalconyDirection;
import com.example.bds.entity.listing.HouseDirection;
import com.example.bds.entity.listing.Listing;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

/**
 * Detail bảng cho nhóm: APARTMENT, MINI_APARTMENT_SERVICE, CONDOTEL
 */
@Entity
@Table(name = "listing_apartment_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDetail extends BasedEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", unique = true, nullable = false)
    private Listing listing;

    // === Phòng ===
    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    // === Tầng ===
    @Column(name = "floor_number")
    private Integer floorNumber;        // căn ở tầng bao nhiêu

    @Column(name = "total_floors")
    private Integer totalFloors;        // tổng số tầng của tòa

    // === Dự án ===
    @Column(name = "block_name", length = 100)
    private String blockName;           // tên block / tòa

    @Column(name = "project_name", length = 255)
    private String projectName;         // tên dự án

    // === Hướng ===
    @Enumerated(EnumType.STRING)
    @Column(name = "balcony_direction", length = 50)
    private BalconyDirection balconyDirection;

    @Enumerated(EnumType.STRING)
    @Column(name = "house_direction", length = 50)
    private HouseDirection houseDirection;

    // === Tiện ích ===
    @Column(name = "has_elevator")
    private Boolean hasElevator;

    @Column(name = "has_pool")
    private Boolean hasPool;

    @Column(name = "has_gym")
    private Boolean hasGym;

    @Column(name = "has_parking")
    private Boolean hasParking;

    // === JSON fallback cho trường đặc biệt ===
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_data", columnDefinition = "JSON")
    private Map<String, Object> extraData;
}
