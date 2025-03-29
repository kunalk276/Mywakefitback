package com.wakefit.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

import com.wakefit.ecommerce.entity.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String mobNo;

   
    private Set<String> roles; 

    private List<OrderDTO> orders;
    private List<AddressDTO> addresses;
    private List<FeedbackDTO> feedbacks;
    private CartDTO cart;
}
