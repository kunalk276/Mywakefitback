package com.wakefit.ecommerce.service;

import java.util.List;

import com.wakefit.ecommerce.entity.Address;

public interface AddressService {
    Address addAddress(Address address);
    Address getAddressById(Long addressId);
    Address updateAddress(Long addressId, Address address);
    void deleteAddress(Long addressId);
	List<Address> findAll();
	 List<Address> getAddressesByUserId(Long userId);
}
