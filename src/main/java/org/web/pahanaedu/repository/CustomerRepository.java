package org.web.pahanaedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.pahanaedu.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    Optional<Customer> findByAccountNumber(String accountNumber);
}
