package com.example.bds.service.interfaces.Listing;

import com.example.bds.entity.listing.PropertyTypeGroup;

/**
 * Generic validator interface cho từng nhóm listing.
 *
 * @param <T> kiểu request DTO tương ứng với nhóm property
 */
public interface ListingTypeValidator<T> {

    /**
     * Nhóm property mà validator này chịu trách nhiệm.
     */
    PropertyTypeGroup supports();

    /**
     * Validate business rules nâng cao (sau khi Bean Validation đã qua).
     * Ném AppException nếu không hợp lệ.
     */
    void validate(T request);
}
