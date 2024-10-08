package com.wakefit.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {

}
