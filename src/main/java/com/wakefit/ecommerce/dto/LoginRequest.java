package com.wakefit.ecommerce.dto;

import java.util.Date;
import java.util.List;

import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;
}
