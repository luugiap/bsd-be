package com.example.bds.entity.listing.detail;

import com.example.bds.entity.BasedEntity;
import com.example.bds.entity.listing.BalconyDirection;
import com.example.bds.entity.listing.HouseDirection;
import com.example.bds.entity.listing.Listing;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Detail bảng cho nhóm: PRIVATE_HOUSE, VILLA_TOWNHOUSE, STREET_HOUSE
 */
@Entity
@Table(name = "listing_house_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseDetail extends BasedEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", unique = true, nullable = false)
    private Listing listing;

    // === Phòng ===
    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    @Column(name = "floors")
    private Integer floors;             // số tầng của nhà

    // === Hướng ===
    @Enumerated(EnumType.STRING)
    @Column(name = "house_direction", length = 50)
    private HouseDirection houseDirection;

    @Enumerated(EnumType.STRING)
    @Column(name = "balcony_direction", length = 50)
    private BalconyDirection balconyDirection;

    // === Kích thước ===
    @Column(name = "front_width", precision = 10, scale = 2)
    private BigDecimal frontWidth;      // mặt tiền (m)

    @Column(name = "road_width", precision = 10, scale = 2)
    private BigDecimal roadWidth;       // chiều rộng đường trước (m)

    @Column(name = "depth", precision = 10, scale = 2)
    private BigDecimal depth;           // chiều sâu (m)

    // === Tiện ích ===
    @Column(name = "has_rooftop")
    private Boolean hasRooftop;

    @Column(name = "has_basement")
    private Boolean hasBasement;

    @Column(name = "has_garage")
    private Boolean hasGarage;

    @Column(name = "has_garden")
    private Boolean hasGarden;

    // === JSON fallback ===
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_data", columnDefinition = "JSON")
    private Map<String, Object> extraData;
}
