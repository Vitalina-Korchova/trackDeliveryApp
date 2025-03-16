package trackDeliveryApp.trackDeliveryApp.service;

import org.springframework.stereotype.Service;
import trackDeliveryApp.trackDeliveryApp.exceptionsHandler.PhoneNumberAlreadyExistsException;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.repository.CustomerRepository;


import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByPhone(customer.getPhone())) {
            throw new PhoneNumberAlreadyExistsException(customer.getPhone());
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String customerId, Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(customerId);
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = existingCustomer.get();

            updatedCustomer.setName(customer.getName());
            updatedCustomer.setEmail(customer.getEmail());
            updatedCustomer.setPhone(customer.getPhone());

            return customerRepository.save(updatedCustomer);
        } else {
            throw new RuntimeException("Customer with ID " + customerId + " not found");
        }
    }
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
