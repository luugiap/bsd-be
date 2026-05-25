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
 * Request tạo listing cho nhóm LAND:
 * PROJECT_LAND | LAND | FARM_RESORT_LAND
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandListingRequest {

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

    private LegalStatus legalStatus;

    @NotBlank private String provinceCode;
    @NotBlank private String districtCode;
    @NotBlank private String wardCode;
    @Size(max = 255) private String addressDetail;

    // === Land-specific fields ===
    private LandType      landType;
    private HouseDirection landDirection;

    @DecimalMin("0.0") private BigDecimal frontWidth;
    @DecimalMin("0.0") private BigDecimal roadWidth;
    @DecimalMin("0.0") private BigDecimal depth;

    private Boolean isCornerLot;
    private Boolean isNearRoad;
    private Boolean hasConstruction;

    @DecimalMin("0.0") private BigDecimal constructionArea;

    private Map<String, Object> extraData;
}
