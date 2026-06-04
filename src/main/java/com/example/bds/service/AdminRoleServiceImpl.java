package com.example.bds.service;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Response.UserRoleResponse;
import com.example.bds.entity.rbac.Roles;
import com.example.bds.entity.rbac.Users;
import com.example.bds.repository.RoleRepository;
import com.example.bds.repository.UserRepository;
import com.example.bds.service.interfaces.AdminRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminRoleServiceImpl implements AdminRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // ---------------------------------------------------------------
    //  GET USER ROLES
    // ---------------------------------------------------------------

    @Override
    public UserRoleResponse getUserRoles(Long userId) {
        Users user = findUserOrThrow(userId);
        log.info("[Admin] Xem roles của userId={}", userId);
        return toResponse(user);
    }

    // ---------------------------------------------------------------
    //  ASSIGN ROLE
    // ---------------------------------------------------------------

    @Override
    @Transactional
    public UserRoleResponse assignRole(Long userId, Long roleId) {
        Users user = findUserOrThrow(userId);
        Roles role = findRoleOrThrow(roleId);

        // Kiểm tra user đã có role này chưa
        boolean alreadyHas = user.getRoles().stream()
                .anyMatch(r -> r.getId().equals(roleId));

        if (alreadyHas) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Người dùng đã có role '" + role.getRoleName() + "'");
        }

        user.getRoles().add(role);
        userRepository.save(user);

        log.info("[Admin] Gán role '{}' (id={}) cho userId={}", role.getRoleName(), roleId, userId);
        return toResponse(user);
    }

    // ---------------------------------------------------------------
    //  REVOKE ROLE
    // ---------------------------------------------------------------

    @Override
    @Transactional
    public UserRoleResponse revokeRole(Long userId, Long roleId) {
        Users user = findUserOrThrow(userId);
        Roles role = findRoleOrThrow(roleId);

        // Kiểm tra user có role này không
        boolean hasRole = user.getRoles().stream()
                .anyMatch(r -> r.getId().equals(roleId));

        if (!hasRole) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Người dùng không có role '" + role.getRoleName() + "'");
        }

        user.getRoles().removeIf(r -> r.getId().equals(roleId));
        userRepository.save(user);

        log.info("[Admin] Xóa role '{}' (id={}) khỏi userId={}", role.getRoleName(), roleId, userId);
        return toResponse(user);
    }

    // ---------------------------------------------------------------
    //  PRIVATE HELPERS
    // ---------------------------------------------------------------

    private Users findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND,
                        "Không tìm thấy người dùng với id=" + userId));
    }

    private Roles findRoleOrThrow(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE,
                        "Không tìm thấy role với id=" + roleId));
    }

    private UserRoleResponse toResponse(Users user) {
        Set<String> roleNames = user.getRoles() == null
                ? Set.of()
                : user.getRoles().stream()
                        .map(Roles::getRoleName)
                        .collect(Collectors.toSet());

        return UserRoleResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .roles(roleNames)
                .build();
    }
}
