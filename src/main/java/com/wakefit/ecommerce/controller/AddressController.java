package com.wakefit.ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wakefit.ecommerce.dto.AddressDTO; // Import the AddressDTO
import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.entity.User;
import com.wakefit.ecommerce.service.AddressService;
import com.wakefit.ecommerce.service.UserService;

@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping("/api/v1/Address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @PostMapping
    public AddressDTO addAddress(@RequestBody AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        Address savedAddress = addressService.addAddress(address);
        return convertToDto(savedAddress);
    }

    @GetMapping("/{addressId}")
    public AddressDTO getAddressById(@PathVariable Long addressId) {
        Address address = addressService.getAddressById(addressId);
        return convertToDto(address);
    }

    @GetMapping("/all")
    public List<AddressDTO> getAddressList() {
        List<Address> addresses = addressService.findAll();
        return addresses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{addressId}")
    public AddressDTO updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        Address updatedAddress = addressService.updateAddress(addressId, address);
        return convertToDto(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
    }

    // Method to convert Address entity to AddressDTO
    private AddressDTO convertToDto(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setLandmark(address.getLandmark());
        addressDTO.setAddress(address.getAddress());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setPincode(address.getPincode());
        // If you need to include userId in the DTO, you can set it here
        // addressDTO.setUserId(address.getUser() != null ? address.getUser().getUserId() : null);
        return addressDTO;
    }

    // Method to convert AddressDTO to Address entity
    private Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setLandmark(addressDTO.getLandmark());
        address.setAddress(addressDTO.getAddress());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPincode(addressDTO.getPincode());
        
        // If you need to set the user, you can fetch the user by userId
        Long userId = addressDTO.getUserId();
        if (userId != null) {
            User user = userService.getUserById(userId);
            address.setUser(user);
        }
        return address;
    }
}