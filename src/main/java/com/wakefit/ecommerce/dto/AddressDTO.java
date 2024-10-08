package com.wakefit.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long addressId;
    private String landmark;
    private String address;
    private String city;
    private String state;
    private String pincode;
    
    private Long userId; // Optional, if you want to track which user this address belongs to
}