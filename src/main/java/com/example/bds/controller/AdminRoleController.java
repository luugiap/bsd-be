package com.example.bds.controller;

import com.example.bds.dto.Request.UserRoleRequest;
import com.example.bds.dto.Response.ApiResponse;
import com.example.bds.dto.Response.UserRoleResponse;
import com.example.bds.service.interfaces.AdminRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * API quản lý phân quyền — chỉ ADMIN được phép gọi.
 *
 * Base path: /api/v1/admin/roles
 *
 *  GET    /api/v1/admin/roles/{userId}      → Xem roles của user
 *  POST   /api/v1/admin/roles/assign        → Gán role cho user
 *  DELETE /api/v1/admin/roles/revoke        → Xóa role khỏi user
 */
@RestController
@RequestMapping("/api/v1/admin/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")           // Tất cả endpoint đều yêu cầu ROLE_ADMIN
public class AdminRoleController {

    private final AdminRoleService adminRoleService;

    // ================================================================
    //  GET /api/v1/admin/roles/{userId}
    //  Xem danh sách roles hiện tại của user
    // ================================================================
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserRoleResponse>> getUserRoles(
            @PathVariable Long userId) {

        UserRoleResponse response = adminRoleService.getUserRoles(userId);
        return ResponseEntity.ok(
                ApiResponse.success(response, "Lấy thông tin roles thành công"));
    }

    // ================================================================
    //  POST /api/v1/admin/roles/assign
    //  Gán thêm role cho user
    //  Body: { "userId": 1, "roleId": 2 }
    // ================================================================
    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<UserRoleResponse>> assignRole(
            @RequestBody @Valid UserRoleRequest request) {

        UserRoleResponse response = adminRoleService.assignRole(
                request.getUserId(), request.getRoleId());
        return ResponseEntity.ok(
                ApiResponse.success(response, "Gán role thành công"));
    }

    // ================================================================
    //  DELETE /api/v1/admin/roles/revoke
    //  Xóa role khỏi user
    //  Body: { "userId": 1, "roleId": 2 }
    // ================================================================
    @DeleteMapping("/revoke")
    public ResponseEntity<ApiResponse<UserRoleResponse>> revokeRole(
            @RequestBody @Valid UserRoleRequest request) {

        UserRoleResponse response = adminRoleService.revokeRole(
                request.getUserId(), request.getRoleId());
        return ResponseEntity.ok(
                ApiResponse.success(response, "Xóa role thành công"));
    }
}
