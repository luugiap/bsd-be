package com.example.bds.dto.Request.Listing;

import com.example.bds.entity.listing.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Request tạo listing cho nhóm HOUSE:
 * PRIVATE_HOUSE | VILLA_TOWNHOUSE | STREET_HOUSE
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseListingRequest {

    @NotNull(message = "Property type là bắt buộc")
    private PropertyType propertyType;

    @NotNull(message = "Business type là bắt buộc")
    private BusinessType businessType;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    private List<String> imageUrls;

    @NotNull(message = "Giá là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull(message = "Diện tích là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal area;

    private LegalStatus    legalStatus;
    private InteriorStatus interiorStatus;

    @NotBlank private String provinceCode;
    @NotBlank private String districtCode;
    @NotBlank private String wardCode;
    @Size(max = 255) private String addressDetail;

    // === House-specific fields ===
    @Min(0) @Max(20) private Integer bedrooms;
    @Min(0) @Max(20) private Integer bathrooms;

    @Min(value = 1, message = "Số tầng phải >= 1")
    @Max(value = 50, message = "Số tầng tối đa 50")
    private Integer floors;

    private HouseDirection   houseDirection;
    private BalconyDirection balconyDirection;

    @DecimalMin("0.0") private BigDecimal frontWidth;  // mặt tiền (m)
    @DecimalMin("0.0") private BigDecimal roadWidth;   // đường trước (m)
    @DecimalMin("0.0") private BigDecimal depth;       // chiều sâu (m)

    private Boolean hasRooftop;
    private Boolean hasBasement;
    private Boolean hasGarage;
    private Boolean hasGarden;

    private Map<String, Object> extraData;
}
