package com.wakefit.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final UserDetailsService userDetailsService;

    public AppSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(request -> new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // CORS Preflight
                        .requestMatchers(HttpMethod.POST, "/api/v1/login", "/api/v1/users/login", "/api/v1/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("Admin")
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority("Admin")
                        .requestMatchers(HttpMethod.GET, "/api/v1/category/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.POST, "/api/v1/category/**").hasAuthority("Admin")
                        .requestMatchers(HttpMethod.POST, "/api/v1/feedback/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.GET, "/api/v1/cart/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.POST, "/api/v1/address/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.GET, "/api/v1/address/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.POST, "/api/v1/order/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.GET, "/api/v1/order/**").hasAnyAuthority("Admin", "customer")
                        .requestMatchers(HttpMethod.POST, "/api/v1/cart/**").hasAuthority("customer")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout.disable()); // Disable logout since we use stateless auth

        return http.build();
    }
}



