package com.wakefit.ecommerce.serviceimplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.AddressRepository;
import com.wakefit.ecommerce.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + addressId));
    }

    @Override
    public Address updateAddress(Long addressId, Address updatedAddress) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);

        if (optionalAddress.isPresent()) {
            Address existingAddress = optionalAddress.get();


            existingAddress.setLandmark(updatedAddress.getLandmark());
            existingAddress.setAddress(updatedAddress.getAddress());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setPincode(updatedAddress.getPincode());


            return addressRepository.save(existingAddress);
        } else {
            throw new ResourceNotFoundException("Address not found with id " + addressId);
        }
    }

    @Override
    public void deleteAddress(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new ResourceNotFoundException("Address not found with id " + addressId);
        }
        addressRepository.deleteById(addressId);
    }

	@Override
	public List<Address> findAll() {
		// TODO Auto-generated method stub
		return addressRepository.findAll();
	}
}
