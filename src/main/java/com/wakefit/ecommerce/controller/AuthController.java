package com.wakefit.ecommerce.controller;

import com.wakefit.ecommerce.dto.LoginRequest;
import com.wakefit.ecommerce.dto.UserDTO;
import com.wakefit.ecommerce.entity.Cart;
import com.wakefit.ecommerce.entity.Role;
import com.wakefit.ecommerce.entity.User;
import com.wakefit.ecommerce.serviceimplement.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import com.wakefit.ecommerce.repository.CartRepository;
import com.wakefit.ecommerce.repository.UserRepository;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://mywakefit-kunal-kadams-projects.vercel.app"}) 
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private UserRepository userRepository;
    

    @PostMapping("/api/v1/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUserName(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Retrieve the authenticated user's details
            User user = userDetailsService.getUserByUserName(loginRequest.getUserName());

            // If the user has no cart, create and assign a new cart
            if (user.getCart() == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart = cartRepository.save(newCart);  // Save new cart in DB
                user.setCart(newCart);
                userRepository.save(user);  // Update user with new cart
            }

            // Prepare the response with user details
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("userName", user.getUserName());
            response.put("email", user.getEmail());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("mobNo", user.getMobNo());
            response.put("role", user.getRoles().stream()
                                 .findFirst()
                                 .map(Role::getRole)
                                 .orElse("No role assigned"));
            response.put("cartId", user.getCart().getCartId());  // Always return a valid cart ID

            return ResponseEntity.ok(response);

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Invalid username or password");
        }
    }



}
