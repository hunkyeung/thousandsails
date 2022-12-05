package net.thousandsails.customer.management.out.adapter.excel;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import net.thousandsails.customer.management.domain.customer.Customer;

import java.io.File;
import java.util.Set;

@Slf4j
public class ExportingCustomerToExcelService {
    public void export(Set<Customer> customers, File file) {
        String outputFile = file.getAbsolutePath() + File.separator + "Result" + System.currentTimeMillis() + ".xlsx";
        System.out.println(outputFile);
        EasyExcel.write(outputFile, Customer.class).sheet("Sheet1").doWrite(customers);
    }
}
