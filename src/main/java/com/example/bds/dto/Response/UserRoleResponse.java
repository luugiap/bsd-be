package com.example.bds.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Response trả về thông tin user kèm danh sách roles hiện tại.
 * Dùng trong các API quản lý phân quyền của admin.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponse {

    private Long   userId;
    private String username;
    private String email;
    private String fullName;

    /** Danh sách tên roles hiện tại của user */
    private Set<String> roles;
}
