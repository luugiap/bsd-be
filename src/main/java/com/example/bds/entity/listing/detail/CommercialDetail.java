package com.example.bds.entity.listing.detail;

import com.example.bds.entity.BasedEntity;
import com.example.bds.entity.listing.Listing;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Detail bảng cho nhóm: OFFICE, KIOSK_SHOP, WAREHOUSE_FACTORY
 */
@Entity
@Table(name = "listing_commercial_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommercialDetail extends BasedEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", unique = true, nullable = false)
    private Listing listing;

    // === Diện tích & Tầng ===
    @Column(name = "floor_area", precision = 18, scale = 2)
    private BigDecimal floorArea;       // diện tích sàn sử dụng (m²)

    @Column(name = "floor_number")
    private Integer floorNumber;        // tầng bao nhiêu

    @Column(name = "total_floors")
    private Integer totalFloors;        // tổng số tầng tòa nhà

    // === Kích thước ===
    @Column(name = "front_width", precision = 10, scale = 2)
    private BigDecimal frontWidth;      // mặt tiền (m)

    @Column(name = "road_width", precision = 10, scale = 2)
    private BigDecimal roadWidth;       // chiều rộng đường (m)

    @Column(name = "ceiling_height", precision = 10, scale = 2)
    private BigDecimal ceilingHeight;   // chiều cao trần (m) – quan trọng cho kho/xưởng

    // === Tiện ích ===
    @Column(name = "has_elevator")
    private Boolean hasElevator;

    @Column(name = "has_parking")
    private Boolean hasParking;

    @Column(name = "has_loading_dock")
    private Boolean hasLoadingDock;     // cửa nhập/xuất hàng (kho, xưởng)

    @Column(name = "has_ac")
    private Boolean hasAc;

    @Column(name = "electricity_capacity")
    private Integer electricityCapacity; // công suất điện (kW) – cho nhà xưởng

    // === JSON fallback ===
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_data", columnDefinition = "JSON")
    private Map<String, Object> extraData;
}
