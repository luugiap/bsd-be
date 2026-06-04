package com.example.bds.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Request body để gán hoặc xóa role khỏi user.
 * Chỉ admin mới được phép gọi.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRequest {

    @NotNull(message = "userId không được để trống")
    private Long userId;

    @NotNull(message = "roleId không được để trống")
    private Long roleId;
}
