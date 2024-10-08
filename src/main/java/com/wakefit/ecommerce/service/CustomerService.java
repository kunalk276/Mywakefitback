package com.wakefit.ecommerce.service;

import java.util.List;

import com.wakefit.ecommerce.entity.Customer;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer getCustomerById(Long customerId);
    List<Customer> getAllCustomers();
    void deleteCustomer(Long customerId);
	Customer updateCustomer(Long customerId, Customer updatedCustomer);
}
