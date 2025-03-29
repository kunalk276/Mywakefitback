package com.wakefit.ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wakefit.ecommerce.dto.AddressDTO;
import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.entity.Order;
import com.wakefit.ecommerce.entity.User;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.service.AddressService;
import com.wakefit.ecommerce.service.OrderService;
import com.wakefit.ecommerce.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        Address savedAddress = addressService.addAddress(address);
        return new ResponseEntity<>(convertToDto(savedAddress), HttpStatus.CREATED);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        Address address = addressService.getAddressById(addressId);
        if (address == null) {
            throw new ResourceNotFoundException("Address not found for ID: " + addressId);
        }
        return new ResponseEntity<>(convertToDto(address), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressDTO>> getAddressList() {
        List<Address> addresses = addressService.findAll();
        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(addressDTOs, HttpStatus.OK);
    }
    @GetMapping("/users/{userId}/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressesByUserId(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        List<Address> addresses = addressService.getAddressesByUserId(userId);
        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(addressDTOs);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        Address updatedAddress = addressService.updateAddress(addressId, address);
        return new ResponseEntity<>(convertToDto(updatedAddress), HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private AddressDTO convertToDto(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setLandmark(address.getLandmark());
        addressDTO.setAddress(address.getAddress());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setPincode(address.getPincode());
        
        if (address.getUser() != null) {
            addressDTO.setUserId(address.getUser().getUserId());
        }

        
        if (address.getOrder() != null) {
            addressDTO.setBillingOrderId(address.getOrder().getOrderId());
        }

        return addressDTO;
    }

    private Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setLandmark(addressDTO.getLandmark());
        address.setAddress(addressDTO.getAddress());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPincode(addressDTO.getPincode());
        
        
        Long userId = addressDTO.getUserId();
        if (userId != null) {
            User user = userService.getUserById(userId);
            address.setUser(user);
        }

       
        Long billingOrderId = addressDTO.getBillingOrderId();
        if (billingOrderId != null) {
            Order billingOrder = orderService.getOrderById(billingOrderId);
            address.setOrder(billingOrder);
        }

        return address;
    }
}
