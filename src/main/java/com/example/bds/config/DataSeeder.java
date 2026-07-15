package com.example.bds.config;

import com.example.bds.entity.rbac.Roles;
import com.example.bds.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findByRoleName("ROLE_USER") == null) {
            Roles role = new Roles();
            role.setRoleName("ROLE_USER");
            role.setDescription("Default role assigned to newly registered users");
            roleRepository.save(role);
        }
    }
}
