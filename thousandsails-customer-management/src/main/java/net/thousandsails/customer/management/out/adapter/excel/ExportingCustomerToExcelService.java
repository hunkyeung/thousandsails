package net.thousandsails.customer.management.out.adapter.excel;

import com.alibaba.excel.EasyExcelFactory;
import lombok.extern.slf4j.Slf4j;
import net.thousandsails.customer.management.domain.customer.Customer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Slf4j
public class ExportingCustomerToExcelService {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public void export(Set<Customer> customers, File file) {
        String outputFile = file.getAbsolutePath() + File.separator + "Result-" + format.format(LocalDateTime.now()) + ".xlsx";
        log.info("匹配结果文件输出路径：{}", outputFile);
        EasyExcelFactory.write(outputFile, Customer.class).sheet("Sheet1").doWrite(customers);
    }
}
