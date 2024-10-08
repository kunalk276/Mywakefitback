package com.wakefit.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String mobNo;
    private String role;

    private List<OrderDTO> orders;
    private List<AddressDTO> addresses;
    private List<FeedbackDTO> feedbacks;
    private CartDTO cart;
}
