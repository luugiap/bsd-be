package com.example.bds.service;


import com.example.bds.entity.CustomUserDetail;
import com.example.bds.entity.rbac.Users;
import com.example.bds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> null);
        return new CustomUserDetail(user);
    }

    public UserDetails loadUserByEmail(String email)  {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        return new CustomUserDetail(user);
    }





}

