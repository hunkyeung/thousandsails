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
import java.util.*;

@Component
@SpringBootApplication
@Slf4j
public class CustomerManagementBootstrap implements ApplicationRunner {
    @Autowired
    PickingOutRepetitiveCustomerApplication application;

    public static void main(String[] args) {
        SpringApplication.run(CustomerManagementBootstrap.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        List<File> fileList = getFileList(args);
        List<Customer> customers = (new LoadingCustomerFromExcelService()).load(fileList);
        Set<Customer> repetitiveCustomers = application.doPickOut(customers);
        (new ExportingCustomerToExcelService()).export(repetitiveCustomers, new File(getOutPath(args)));
    }

    private List<File> getFileList(ApplicationArguments args) {
        String inOptionName = "in";
        List<String> filePathList = new ArrayList<>();
        if (args.containsOption(inOptionName)) {
            filePathList.addAll(args.getOptionValues(inOptionName));
        } else {
            String defaultPath = System.getProperty("user.dir");
            log.warn("未指定文件输入目录，程序将使用程序运行根目录作为根目录：{}", defaultPath);
            filePathList.add(defaultPath);
        }
        List<File> fileList = new ArrayList<>();
        filePathList.forEach(filePath -> {
            File file = new File(filePath);
            if (file.exists()) {
                String suffix = ".xlsx";
                if (file.isFile()) {
                    if (file.getName().endsWith(suffix)) {
                        fileList.add(file);
                    } else {
                        log.warn("指定文件名不合法，请指定Excel后缀的文件：{}", file.getAbsolutePath());
                    }
                } else {
                    File[] files = file.listFiles((dir, name) -> name.endsWith(suffix));
                    if (!Objects.isNull(files))
                        fileList.addAll(Arrays.stream(files).toList());
                }
            } else {
                log.warn("指定文件或者目录不存在，程序将忽略该文件或目录：{}", filePath);
            }
        });
        if (fileList.isEmpty()) {
            throw new IllegalStateException(String.format("参数【--in】指定的文件或目录全部无效：%s，请确认正确后，重新运行程序", filePathList));
        }
        return fileList;
    }

    private String getOutPath(ApplicationArguments args) {
        String outOptionName = "out";
        String outPath = "result";
        if (args.containsOption(outOptionName)) {
            outPath = args.getOptionValues(outOptionName).get(0);
        }
        File file = new File(outPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return outPath;
    }
}