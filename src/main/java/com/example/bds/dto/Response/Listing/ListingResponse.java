package com.example.bds.dto.Response.Listing;

import com.example.bds.entity.listing.BusinessType;
import com.example.bds.entity.listing.ListingStatus;
import com.example.bds.entity.listing.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingResponse {

    private Long        id;
    private String      title;
    private String      description;
    private PropertyType propertyType;
    private BusinessType businessType;
    private ListingStatus status;

    // Price & Area
    private BigDecimal price;
    private BigDecimal area;
    private BigDecimal pricePerM2;

    // Location
    private String provinceCode;
    private String provinceName;
    private String districtCode;
    private String districtName;
    private String wardCode;
    private String wardName;
    private String addressDetail;

    // Detail (chỉ 1 trong số này có data tuỳ propertyType)
    private Map<String, Object> detail;
}
