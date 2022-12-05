package net.thousandsails.customer.management.domain.customer;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @ExcelProperty("客 户 名 称")
    private String name;
    @ExcelProperty("邮  箱")
    private String email;
    @ExcelProperty("跟 单 员")
    private String merchandiser;


    @ExcelIgnore
    private boolean matchingEmailAndName;
    @ExcelIgnore
    private boolean matchingEmail;
    @ExcelIgnore
    private boolean matchingName;

    public boolean isRepetitive() {
        return matchingEmailAndName || matchingEmail || matchingName;
    }

    public void match(Customer other) {
        if (Objects.equals(merchandiser, other.merchandiser) || isRepetitive()) {
            return;
        }
        if (Objects.equals(email, other.email) && Objects.equals(name, other.name)) {
            matchingEmailAndName = true;
            other.matchingEmailAndName = true;
        } else if (Objects.equals(email, other.email)) {
            matchingEmail = true;
            other.matchingEmail = true;
        } else if (Objects.equals(name, other.name)) {
            matchingName = true;
            other.matchingName = true;
        }
    }
}
