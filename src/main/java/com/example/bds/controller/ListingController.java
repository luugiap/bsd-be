package com.example.bds.controller;

import com.example.bds.dto.Request.Listing.*;
import com.example.bds.dto.Response.ApiResponse;
import com.example.bds.dto.Response.Listing.ListingResponse;
import com.example.bds.dto.Response.Listing.PagedListingResponse;
import com.example.bds.entity.rbac.Users;
import com.example.bds.repository.UserRepository;
import com.example.bds.service.interfaces.Listing.OwnerListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/listing")
@RequiredArgsConstructor
public class ListingController {

    private final OwnerListingService ownerListingService;
    private final UserRepository userRepository;

    // ================================================================
    //  Helper: lấy Users entity từ principal (username)
    // ================================================================
    private Users resolveUser(UserDetails principal) {
        System.out.println("aaaaa");

        System.out.println("xxxxxx");

        System.out.println("dhsfjf");

        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Người dùng không tồn tại"));
    }

    // ================================================================
    //  POST /api/v1/listing/apartment
    //  Tạo tin rao chung cư / căn hộ dịch vụ / condotel
    // ================================================================
    @PostMapping("/apartment")
    public ResponseEntity<ApiResponse<ListingResponse>> createApartment(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody @Valid ApartmentListingRequest request) {

        Users owner = resolveUser(principal);
        ListingResponse response = ownerListingService.createApartment(owner, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tạo tin chung cư thành công"));
    }

    // ================================================================
    //  POST /api/v1/listing/house
    //  Tạo tin rao nhà riêng / nhà phố / biệt thự
    // ================================================================
    @PostMapping("/house")
    public ResponseEntity<ApiResponse<ListingResponse>> createHouse(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody @Valid HouseListingRequest request) {

        Users owner = resolveUser(principal);
        ListingResponse response = ownerListingService.createHouse(owner, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tạo tin nhà ở thành công"));
    }

    // ================================================================
    //  POST /api/v1/listing/land
    //  Tạo tin rao đất nền / đất nông nghiệp / đất công nghiệp
    // ================================================================
    @PostMapping("/land")
    public ResponseEntity<ApiResponse<ListingResponse>> createLand(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody @Valid LandListingRequest request) {

        Users owner = resolveUser(principal);
        ListingResponse response = ownerListingService.createLand(owner, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tạo tin đất thành công"));
    }

    // ================================================================
    //  POST /api/v1/listing/commercial
    //  Tạo tin rao mặt bằng / văn phòng / kho xưởng
    // ================================================================
    @PostMapping("/commercial")
    public ResponseEntity<ApiResponse<ListingResponse>> createCommercial(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody @Valid CommercialListingRequest request) {

        Users owner = resolveUser(principal);
        ListingResponse response = ownerListingService.createCommercial(owner, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tạo tin thương mại thành công"));
    }

    // ================================================================
    //  POST /api/v1/listing/mini-room
    //  Tạo tin rao phòng trọ / nhà trọ mini
    // ================================================================
    // ================================================================
    //  GET /api/v1/listing/search
    //  Tìm kiếm bài đăng. Nếu không có kết quả → fallback trả về tất cả
    // ================================================================
    @GetMapping("/search")
    public ResponseEntity<PagedListingResponse> search(
            @RequestParam String text,
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String districtCode,
            @RequestParam(required = false) String wardCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PagedListingResponse response = ownerListingService.search(
                text, provinceCode, districtCode, wardCode, page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mini-room")
    public ResponseEntity<ApiResponse<ListingResponse>> createMiniRoom(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody @Valid MiniRoomListingRequest request) {

        Users owner = resolveUser(principal);
        ListingResponse response = ownerListingService.createMiniRoom(owner, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tạo tin phòng trọ thành công"));
    }
}
