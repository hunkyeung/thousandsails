package net.thousandsails;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import net.thousandsails.customer.management.domain.customer.Customer;
import net.thousandsails.customer.management.in.adapter.excel.LoadingCustomerFromExcelService;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

@Slf4j
public class ExcelReadTest {
    @Test
    void read() {
        String fileName = "demo.xlsx";
        EasyExcel.read(fileName, Customer.class, new ReadListener() {
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                System.out.println("invoke");
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("finish");
            }
        }).doReadAll();
    }

    @Test
    void readUsingService() {
        LoadingCustomerFromExcelService service = new LoadingCustomerFromExcelService();
        List<Customer> customerList = service.load(List.of(new File("demo.xlsx")));
        System.out.println(customerList);
    }
}
