package net.thousandsails;


import lombok.extern.slf4j.Slf4j;
import net.thousandsails.customer.management.application.PickingOutRepetitiveCustomerApplication;
import net.thousandsails.customer.management.domain.customer.Customer;
import net.thousandsails.customer.management.in.adapter.excel.LoadingCustomerFromExcelService;
import net.thousandsails.customer.management.out.adapter.excel.ExportingCustomerToExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Set;

@Component
@SpringBootApplication
@Slf4j
public class CustomerManagementBootstrap implements ApplicationRunner {
    @Autowired
    PickingOutRepetitiveCustomerApplication application;

    public static void main(String[] args) {
        System.out.println(args.length);
        SpringApplication.run(CustomerManagementBootstrap.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LoadingCustomerFromExcelService loadingService = new LoadingCustomerFromExcelService();
        List<Customer> customers = loadingService.load(List.of(new File("demo.xlsx")));
        Set<Customer> repetitiveCustomers = application.doPickOut(customers);
        ExportingCustomerToExcelService exportingService = new ExportingCustomerToExcelService();
        exportingService.export(repetitiveCustomers, new File(""));
    }
}