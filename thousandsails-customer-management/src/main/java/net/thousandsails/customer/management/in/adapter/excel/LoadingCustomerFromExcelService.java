package net.thousandsails.customer.management.in.adapter.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import net.thousandsails.customer.management.domain.customer.Customer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class LoadingCustomerFromExcelService {
    public List<Customer> load(List<File> files) {
        List<Customer> customers = new ArrayList<>();
        if (Objects.isNull(files)) {
            throw new IllegalArgumentException("The FilePathList could not be null.");
        }
        files.forEach(file -> EasyExcel.read(file, Customer.class, new ReadListener<Customer>() {
            @Override
            public void invoke(Customer customer, AnalysisContext analysisContext) {
                customers.add(customer);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).doReadAll());
        log.info("There was {} data was loaded.", customers.size());
        return customers;
    }
}
