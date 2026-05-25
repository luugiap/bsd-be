package com.example.bds.entity.listing.detail;

import com.example.bds.entity.BasedEntity;
import com.example.bds.entity.listing.HouseDirection;
import com.example.bds.entity.listing.LandType;
import com.example.bds.entity.listing.Listing;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Detail bảng cho nhóm: PROJECT_LAND, LAND, FARM_RESORT_LAND
 */
@Entity
@Table(name = "listing_land_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandDetail extends BasedEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", unique = true, nullable = false)
    private Listing listing;

    // === Phân loại đất ===
    @Enumerated(EnumType.STRING)
    @Column(name = "land_type", length = 50)
    private LandType landType;

    // === Hướng ===
    @Enumerated(EnumType.STRING)
    @Column(name = "land_direction", length = 50)
    private HouseDirection landDirection;

    // === Kích thước ===
    @Column(name = "front_width", precision = 10, scale = 2)
    private BigDecimal frontWidth;      // mặt tiền (m)

    @Column(name = "road_width", precision = 10, scale = 2)
    private BigDecimal roadWidth;       // chiều rộng đường (m)

    @Column(name = "depth", precision = 10, scale = 2)
    private BigDecimal depth;           // chiều sâu (m)

    // === Đặc điểm lô đất ===
    @Column(name = "is_corner_lot")
    private Boolean isCornerLot;        // đất góc

    @Column(name = "is_near_road")
    private Boolean isNearRoad;         // mặt đường lớn

    @Column(name = "has_construction")
    private Boolean hasConstruction;    // có công trình trên đất

    @Column(name = "construction_area", precision = 18, scale = 2)
    private BigDecimal constructionArea; // diện tích xây dựng nếu có

    // === JSON fallback ===
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_data", columnDefinition = "JSON")
    private Map<String, Object> extraData;
}
