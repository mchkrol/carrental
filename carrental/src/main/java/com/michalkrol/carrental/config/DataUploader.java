package com.michalkrol.carrental.config;

import com.michalkrol.carrental.entity.Customer;
import com.michalkrol.carrental.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataUploader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final CustomerDataManager customerDataManager;

    public DataUploader(CustomerRepository customerRepository,
                        CustomerDataManager customerDataManager) {
        this.customerRepository = customerRepository;
        this.customerDataManager = customerDataManager;
    }

    @Override
    public void run(String... args) {
        if (customerRepository.count() == 0) {
            customerDataManager.getData().forEach(config -> {
                Customer customer = Customer.builder()
                        .name(config.getName())
                        .build();
                customerRepository.save(customer);
            });
            System.out.println("Sample customers loaded into database.");
        } else {
            System.out.println("Customers already present, skipping data initialization.");
        }
    }
}
