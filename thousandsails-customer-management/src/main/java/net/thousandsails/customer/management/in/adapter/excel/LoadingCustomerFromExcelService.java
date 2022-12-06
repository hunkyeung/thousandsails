package net.thousandsails.customer.management.in.adapter.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import net.thousandsails.customer.management.domain.customer.Customer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoadingCustomerFromExcelService {
    public List<Customer> load(List<File> files) {
        List<Customer> customers = new ArrayList<>();
        files.forEach(file -> EasyExcelFactory.read(file, Customer.class, new ReadListener<Customer>() {
            @Override
            public void invoke(Customer customer, AnalysisContext analysisContext) {
                customers.add(customer);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                log.info("完成{}文件加载", file.getAbsolutePath());
            }
        }).doReadAll());
        log.info("总共加载了{}条记录", customers.size());
        return customers;
    }
}
