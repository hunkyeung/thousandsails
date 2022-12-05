package net.thousandsails.customer.management.application;

import net.thousandsails.customer.management.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PickingOutRepetitiveCustomerApplication {
    public Set<Customer> doPickOut(List<Customer> customerList) {
       process(customerList);
       return customerList.stream().filter(customer -> customer.isRepetitive()).collect(Collectors.toSet());
    }

    private void process(List<Customer> customerList) {
        for (int i = 0; i < customerList.size(); i++) {
            Customer iCustomer = customerList.get(0);
            for (int j = i + 1; j < customerList.size(); j++) {
                Customer jCustomer = customerList.get(j);
                iCustomer.match(jCustomer);
            }
        }
    }
}
