package com.wakefit.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;





@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeHttpRequests()
//                .requestMatchers("/api/v1/users/login", "/api/v1/users/register", "/api/v3/products", "/api/v1/category/all").permitAll() // Public access
//                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll() // Public access for certain GET requests
//                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("Admin") // Admin access for PUT requests
//                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("Admin") // Admin access for POST requests
//                .requestMatchers(HttpMethod.POST, "/api/v3/products/**").hasAuthority("Admin") // Admin access for POST requests
//                .requestMatchers(HttpMethod.POST, "/api/v1/category/**").hasAuthority("Admin") // Admin access for category POST requests
//                .requestMatchers(HttpMethod.POST, "/api/v1/feedback/**").hasAuthority("Admin") // Admin access for feedback
//                .requestMatchers(HttpMethod.GET, "/api/v1/cart/**").hasAnyAuthority("Admin", "customer") // Both roles can access cart
//                .requestMatchers(HttpMethod.POST, "/api/v1/Address/**").hasAuthority("Admin") // Admin access for address
//                .requestMatchers(HttpMethod.GET, "/api/v3/products/search").hasAnyAuthority("customer") // Customer access for product search
//                .requestMatchers(HttpMethod.PUT, "/appointments/**").hasAuthority("Admin") // Admin access for appointments
//                .anyRequest().authenticated() // All other requests require authentication
//            .and()
//                .formLogin().permitAll() 
//            .and()
//                .httpBasic(); 
//    }
//
//}
    
   
    
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
        .cors().and()  
        .csrf().disable()
            .authorizeHttpRequests(auth -> auth
            		
                .requestMatchers("/api/v1/users/login", "/api/v1/users/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyAuthority("Admin","customer")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority("Admin","customer")
                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("Admin")
                .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/api/v1/category/**").hasAnyAuthority("Admin", "customer")
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAnyAuthority("Admin", "customer")
                .requestMatchers(HttpMethod.POST, "/api/v1/category/**").hasAuthority("Admin")
                .requestMatchers(HttpMethod.POST, "/api/v1/feedback/**").hasAnyAuthority("Admin" ,"customer")
                .requestMatchers(HttpMethod.GET, "/api/v1/cart/**").hasAnyAuthority("Admin" ,"customer")
                .requestMatchers(HttpMethod.POST, "/api/v1/address/**").hasAnyAuthority("Admin" ,"customer")
                .requestMatchers(HttpMethod.GET, "/api/v1/address/**").hasAnyAuthority("Admin" ,"customer")
                .requestMatchers(HttpMethod.POST, "/api/v1/order**").hasAnyAuthority("Admin" ,"customer")
                .requestMatchers(HttpMethod.GET, "/api/v1/order**").hasAnyAuthority("Admin" ,"customer")
                
                .requestMatchers(HttpMethod.POST, "/api/v1/cart/**").hasAuthority("customer")
                
                .anyRequest().authenticated()
            )
            
            .httpBasic();

        return http.build();
    }
    
    

}
