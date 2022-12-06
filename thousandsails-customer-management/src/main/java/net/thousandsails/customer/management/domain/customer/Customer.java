package net.thousandsails.customer.management.domain.customer;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
    @ExcelProperty("序号")
    private String seqNumber;
    @ExcelProperty("客 户 名 称")
    private String name;
    @ExcelProperty("邮  箱")
    private String email;
    @ExcelProperty("跟 单 员")
    private String merchandiser;
    @ExcelProperty("匹配逻辑")
    private String matchingLogic;

}
