package com.example.bds.service.ListingImplementations;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Request.Listing.ApartmentListingRequest;
import com.example.bds.entity.listing.PropertyTypeGroup;
import com.example.bds.service.interfaces.Listing.ListingTypeValidator;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Validator cho nhóm APARTMENT: APARTMENT, MINI_APARTMENT_SERVICE, CONDOTEL
 */
@Service
public class ApartmentListingValidator implements ListingTypeValidator<ApartmentListingRequest> {

    // Whitelist các key được phép trong extraData JSON
    private static final Set<String> ALLOWED_EXTRA_KEYS = Set.of(
            "apartment_code",    // mã căn hộ
            "furniture_list",    // danh sách nội thất
            "view_description"   // mô tả view
    );

    @Override
    public PropertyTypeGroup supports() {
        return PropertyTypeGroup.APARTMENT;
    }

    @Override
    public void validate(ApartmentListingRequest request) {
        // 1. Kiểm tra propertyType có thuộc nhóm APARTMENT không
        if (PropertyTypeGroup.from(request.getPropertyType()) != PropertyTypeGroup.APARTMENT) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "PropertyType '" + request.getPropertyType() + "' không hợp lệ cho Apartment listing");
        }

        // 2. floorNumber không được lớn hơn totalFloors
        if (request.getFloorNumber() != null && request.getTotalFloors() != null) {
            if (request.getFloorNumber() > request.getTotalFloors()) {
                throw new AppException(ErrorCode.VALIDATION_ERROR,
                        "Số tầng căn hộ (" + request.getFloorNumber() +
                        ") không thể lớn hơn tổng số tầng tòa (" + request.getTotalFloors() + ")");
            }
        }

        // 3. Validate extraData JSON keys
        if (request.getExtraData() != null) {
            validateExtraDataKeys(request.getExtraData().keySet(), ALLOWED_EXTRA_KEYS);
        }
    }

    private void validateExtraDataKeys(Set<String> actualKeys, Set<String> allowedKeys) {
        for (String key : actualKeys) {
            if (!allowedKeys.contains(key)) {
                throw new AppException(ErrorCode.VALIDATION_ERROR,
                        "extraData chứa key không hợp lệ: '" + key + "'");
            }
        }
    }
}
