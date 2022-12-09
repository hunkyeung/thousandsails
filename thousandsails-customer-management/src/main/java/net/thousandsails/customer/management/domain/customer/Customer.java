package net.thousandsails.customer.management.domain.customer;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
    @ExcelProperty("序号")
    private String seqNumber;
    @ExcelProperty(value = "公  司  名  称", converter = KeywordConverter.class)
    private String name;
    @ExcelProperty(value = "联  系  人", converter = KeywordConverter.class)
    private String contacts;
    @ExcelProperty(value = "E-mail", converter = KeywordConverter.class)
    private String email;
    @ExcelProperty("国家/城市")
    private String country;
    @ExcelProperty("地址")
    private String address;
    @ExcelProperty(value = "跟单业务员")
    private String merchandiser;
    @ExcelProperty("匹配逻辑")
    private String matchingLogic;

}

