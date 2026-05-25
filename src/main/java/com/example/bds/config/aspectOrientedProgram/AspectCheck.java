package com.example.bds.config.aspectOrientedProgram;


import com.example.bds.config.annotation.CheckRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Aspect
public class AspectCheck {

    @Around("@annotation(checkRole)")
    public Object checkRoleAOP(ProceedingJoinPoint joinPoint, CheckRole checkRole) throws Throwable {
        List<String> roleNeed = Arrays.asList(checkRole.value());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is null");
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> userRoles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        boolean userRoleMatch = roleNeed.stream().anyMatch(userRoles::contains);
        if (!userRoleMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication Failed");
        }
        return joinPoint.proceed();

        }

    }

