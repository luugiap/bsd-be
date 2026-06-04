package com.example.bds.service.interfaces;

import com.example.bds.dto.Response.UserRoleResponse;

/**
 * Interface quản lý phân quyền (chỉ ADMIN được gọi).
 */
public interface AdminRoleService {

    /**
     * Lấy thông tin user kèm danh sách roles hiện tại.
     *
     * @param userId ID của user cần xem
     * @return UserRoleResponse chứa roles hiện tại
     */
    UserRoleResponse getUserRoles(Long userId);

    /**
     * Gán thêm một role vào user.
     *
     * @param userId ID của user
     * @param roleId ID của role cần gán
     * @return UserRoleResponse sau khi thêm
     */
    UserRoleResponse assignRole(Long userId, Long roleId);

    /**
     * Xóa một role khỏi user.
     *
     * @param userId ID của user
     * @param roleId ID của role cần xóa
     * @return UserRoleResponse sau khi xóa
     */
    UserRoleResponse revokeRole(Long userId, Long roleId);
}
