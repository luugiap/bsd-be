package com.example.bds.config;

import com.example.bds.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component

public class EmailAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserService customUserService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailAuthenticationToken token = (EmailAuthenticationToken) authentication;
        String email = token.getPrincipal().toString();
        String password = token.getCredentials().toString();
        UserDetails userDetails = customUserService.loadUserByEmail(email);
        if(!userDetails.getPassword().equals(passwordEncoder.encode(password))){
            throw new BadCredentialsException("Invalid email or password");
        }
        EmailAuthenticationToken authenticated = new EmailAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticated.setDetails(new WebAuthenticationDetailsSource());
        return authenticated;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
