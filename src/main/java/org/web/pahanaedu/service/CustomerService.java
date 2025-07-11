package org.web.pahanaedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.pahanaedu.model.Customer;
import org.web.pahanaedu.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerByAccountNumber(String accountNumber)
    {
        return customerRepository.findByAccountNumber(accountNumber);
    }

    public Customer updateCustomer(String accountNumber, Customer updatedCustomer)
    {
        return customerRepository.findByAccountNumber(accountNumber)
                .map(existing -> {
                    existing.setName(updatedCustomer.getName());
                    existing.setAddress(updatedCustomer.getAddress());
                    existing.setPhone(updatedCustomer.getPhone());
                    existing.setUnitsConsumed(updatedCustomer.getUnitsConsumed());
                    return customerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found!"));
    }

    public void deleteCustomer(String accountNumber)
    {
        customerRepository.findByAccountNumber(accountNumber)
                .ifPresent(customerRepository::delete);
    }

    public double calculateBill(String accountNumber) {
        Customer customer = customerRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        double ratePerUnit = 10.0;
        return customer.getUnitsConsumed() * ratePerUnit;
    }

}
