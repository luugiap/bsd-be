package com.example.bds.entity.listing;

import com.example.bds.entity.BasedEntity;
import com.example.bds.entity.listing.detail.*;
import com.example.bds.entity.location.District;
import com.example.bds.entity.location.Province;
import com.example.bds.entity.location.Ward;
import com.example.bds.entity.rbac.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "listing")
@Entity
@Setter
@Getter
public class Listing extends BasedEntity {

    // ================= OWNER =================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // ================= LOẠI BĐS =================
    /**
     * Loại bất động sản (chi tiết). Dùng để xác định nhóm detail.
     * Dùng PropertyTypeGroup.from(propertyType) để lấy nhóm.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false, length = 50)
    private PropertyType propertyType;

    /**
     * Mục đích: Bán hay cho thuê.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "business_type", length = 20)
    private BusinessType businessType;

    // ================= CONTENT =================
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "url_images", columnDefinition = "TEXT")
    private String urlImages;

    // ================= PRICE & AREA =================
    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

    /** Diện tích chính (m²): đất dùng diện tích đất, căn hộ/nhà dùng diện tích sàn */
    @Column(name = "area", precision = 18, scale = 2)
    private BigDecimal area;

    @Column(name = "price_per_m2", precision = 18, scale = 2)
    private BigDecimal pricePerM2;

    // ================= CHUNG NHIỀU LOẠI =================
    @Enumerated(EnumType.STRING)
    @Column(name = "legal_status", length = 100)
    private LegalStatus legalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "interior_status", length = 100)
    private InteriorStatus interiorStatus;

    // ================= LOCATION =================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code")
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_code")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_code")
    private Ward ward;

    @Column(name = "address_detail", length = 255)
    private String addressDetail;

    // ================= STATUS =================
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private ListingStatus status = ListingStatus.PENDING;

    // ================= JSON FALLBACK (cho OTHER hoặc extra đặc biệt) =================
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dynamic_data", columnDefinition = "JSON")
    private Map<String, Object> dynamicData;

    // ================= DETAIL RELATIONS =================
    // Chỉ 1 trong 5 cái này được populate tuỳ theo propertyType

    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ApartmentDetail apartmentDetail;

    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private HouseDetail houseDetail;

    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LandDetail landDetail;

    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CommercialDetail commercialDetail;

    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MiniRoomDetail miniRoomDetail;
}
