package org.web.pahanaedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web.pahanaedu.model.Customer;
import org.web.pahanaedu.service.CustomerService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*") //This allows access from frontend
@RestController
@RequestMapping("/api/customers")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer)
    {
        return customerService.addCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers()
    {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{accountNumber}")
    public Optional<Customer> getCustomer(@PathVariable String accountNumber)
    {
        return customerService.getCustomerByAccountNumber(accountNumber);
    }

    @GetMapping("/{accountNumber}/bill")
    public double getCustomerBill(@PathVariable String accountNumber) {
        return customerService.calculateBill(accountNumber);
    }

    @PutMapping("/{accountNumber}")
    public Customer updateCustomer(@PathVariable String accountNumber, @RequestBody Customer customer)
    {
        return customerService.updateCustomer(accountNumber, customer);
    }

    @DeleteMapping("/{accountNumber}")
    public void deleteCustomer(@PathVariable String accountNumber)
    {
        customerService.deleteCustomer(accountNumber);
    }
}
