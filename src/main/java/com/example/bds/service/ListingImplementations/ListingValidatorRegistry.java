package com.example.bds.service.ListingImplementations;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.entity.listing.PropertyTypeGroup;
import com.example.bds.service.interfaces.Listing.ListingTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Registry tự động resolve đúng validator dựa trên PropertyTypeGroup.
 * Spring inject tất cả beans implement ListingTypeValidator<?>.
 */
@Service
@RequiredArgsConstructor
public class ListingValidatorRegistry {

    private final List<ListingTypeValidator<?>> validators;

    /**
     * Lấy validator tương ứng với group.
     * Cast unsafe nhưng được đảm bảo bởi từng validator chỉ xử lý đúng type của nó.
     */
    @SuppressWarnings("unchecked")
    public <T> ListingTypeValidator<T> getValidator(PropertyTypeGroup group) {
        return (ListingTypeValidator<T>) validators.stream()
                .filter(v -> v.supports() == group)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.VALIDATION_ERROR,
                        "Không tìm thấy validator cho loại BĐS: " + group));
    }
}
