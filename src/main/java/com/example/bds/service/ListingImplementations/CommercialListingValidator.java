package com.example.bds.service.ListingImplementations;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Request.Listing.CommercialListingRequest;
import com.example.bds.entity.listing.PropertyTypeGroup;
import com.example.bds.service.interfaces.Listing.ListingTypeValidator;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Validator cho nhóm COMMERCIAL: OFFICE, KIOSK_SHOP, WAREHOUSE_FACTORY
 */
@Service
public class CommercialListingValidator implements ListingTypeValidator<CommercialListingRequest> {

    private static final Set<String> ALLOWED_EXTRA_KEYS = Set.of(
            "fire_safety_cert",     // chứng nhận PCCC
            "building_grade",       // hạng tòa nhà (A, B, C)
            "management_fee"        // phí quản lý (VNĐ/m²/tháng)
    );

    @Override
    public PropertyTypeGroup supports() {
        return PropertyTypeGroup.COMMERCIAL;
    }

    @Override
    public void validate(CommercialListingRequest request) {
        // 1. Kiểm tra nhóm
        if (PropertyTypeGroup.from(request.getPropertyType()) != PropertyTypeGroup.COMMERCIAL) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "PropertyType '" + request.getPropertyType() + "' không hợp lệ cho Commercial listing");
        }

        // 2. floorNumber không lớn hơn totalFloors
        if (request.getFloorNumber() != null && request.getTotalFloors() != null) {
            if (request.getFloorNumber() > request.getTotalFloors()) {
                throw new AppException(ErrorCode.VALIDATION_ERROR,
                        "Số tầng (" + request.getFloorNumber() +
                        ") không thể lớn hơn tổng số tầng (" + request.getTotalFloors() + ")");
            }
        }

        // 3. Validate extraData
        if (request.getExtraData() != null) {
            for (String key : request.getExtraData().keySet()) {
                if (!ALLOWED_EXTRA_KEYS.contains(key)) {
                    throw new AppException(ErrorCode.VALIDATION_ERROR,
                            "extraData chứa key không hợp lệ: '" + key + "'");
                }
            }
        }
    }
}
