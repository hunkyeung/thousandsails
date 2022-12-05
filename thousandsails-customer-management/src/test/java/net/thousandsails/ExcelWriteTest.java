package net.thousandsails;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import net.thousandsails.customer.management.domain.customer.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class ExcelWriteTest {
    @Test
    void write() {
        String fileName = System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, Customer.class).sheet("模板").doWrite(List.of(new Customer("abc", "123@abc.com", "张三")));
    }
}
