package com.example.bds.service.interfaces.Listing;

import com.example.bds.dto.Request.Listing.*;
import com.example.bds.dto.Response.Listing.ListingResponse;
import com.example.bds.dto.Response.Listing.PagedListingResponse;
import com.example.bds.entity.rbac.Users;

public interface OwnerListingService {

    // === Tạo listing theo từng nhóm ===
    ListingResponse createApartment(Users owner, ApartmentListingRequest request);
    ListingResponse createHouse(Users owner, HouseListingRequest request);
    ListingResponse createLand(Users owner, LandListingRequest request);
    ListingResponse createCommercial(Users owner, CommercialListingRequest request);
    ListingResponse createMiniRoom(Users owner, MiniRoomListingRequest request);

    // === Tìm kiếm với fallback ===
    PagedListingResponse search(String text, String provinceCode, String districtCode,
                                String wardCode, int page, int size);
}
