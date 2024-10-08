package com.wakefit.ecommerce.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@DiscriminatorValue("customer")
public class Customer extends User {


}



//customer is user in wakefit   and admin has hard coded login details
//customer user table store customer value by role
// also admin details in user table but one admin details by store value role as ADMIN
//admin do not have attributes like user(customer) only contain user name and password