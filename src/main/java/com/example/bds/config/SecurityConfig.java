package com.example.bds.config;

import com.example.bds.repository.UserRepository;
import com.example.bds.security.JwtAuthFilter;
import com.example.bds.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
   private final CustomUserService customUserService;
   private final JwtAuthFilter jwtAuthFilter;
   private final UserRepository userRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           http.csrf(AbstractHttpConfigurer::disable);
           http.cors(cors -> {

               cors.configurationSource(
                       https -> {
                           CorsConfiguration corsConfiguration = new CorsConfiguration();
                           corsConfiguration.setAllowedOrigins(
                                   List.of("*")
                           );
                           corsConfiguration.setAllowedMethods(
                                   List.of("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS")
                           );
                           corsConfiguration.setAllowedHeaders(List.of("*"));
                           return corsConfiguration;
                       }
               );
           });
           http.sessionManagement(sessionManagement ->
                   sessionManagement.sessionCreationPolicy((SessionCreationPolicy.STATELESS)));
           http.authorizeHttpRequests(authorizeRequests ->
                   authorizeRequests.requestMatchers(
"api/v1/user/login"
                   ).permitAll()
                           .requestMatchers("/api/v1/user/register").permitAll()
                           .requestMatchers("/api/v1/user/step1").permitAll()
                           .anyRequest().authenticated());
           http.authenticationProvider(daoAuthenticationProvider());


           http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
return http.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }








}
