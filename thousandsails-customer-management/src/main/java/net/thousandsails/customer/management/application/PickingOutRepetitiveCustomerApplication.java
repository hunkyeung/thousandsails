package net.thousandsails.customer.management.application;

import lombok.extern.slf4j.Slf4j;
import net.thousandsails.customer.management.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class PickingOutRepetitiveCustomerApplication {
    public Set<Customer> doPickOut(List<Customer> customerList) {
        Set<Customer> toBeConfirmedCustomerSet = new HashSet<>(customerList);
        Set<Customer> repetitiveCustomers = new HashSet<>();
        Set<Customer> toBeConfirmedEmailCustomerSet =
                process(repetitiveCustomers,
                        toBeConfirmedCustomerSet,
                        new MyMatchingLogic() {

                            @Override
                            public String matchingLogicName() {
                                return "邮箱和名称";
                            }

                            @Override
                            public Boolean test(Customer one, Customer another) {
                                return Objects.equals(one.getEmail(), another.getEmail()) && Objects.equals(one.getName(), another.getName());
                            }
                        });
        Set<Customer> toBeConfirmedNameCustomerSet =
                process(repetitiveCustomers,
                        toBeConfirmedEmailCustomerSet,
                        new MyMatchingLogic() {

                            @Override
                            public String matchingLogicName() {
                                return "邮箱";
                            }

                            @Override
                            public Boolean test(Customer one, Customer another) {
                                return Objects.equals(one.getEmail(), another.getEmail());
                            }
                        });
        process(repetitiveCustomers,
                toBeConfirmedNameCustomerSet,
                new MyMatchingLogic() {

                    @Override
                    public String matchingLogicName() {
                        return "名称";
                    }

                    @Override
                    public Boolean test(Customer one, Customer another) {
                        return Objects.equals(one.getName(), another.getName());
                    }
                });
        return repetitiveCustomers;
    }

    private Set<Customer> process(Set<Customer> repetitiveCustomers,
                                  Set<Customer> toBeConfirmedCustomers,
                                  MyMatchingLogic matchingLogic) {
        List<Customer> toBeConfirmedCustomerList = toBeConfirmedCustomers.stream().toList();
        toBeConfirmedCustomers.clear();
        for (int i = 0; i < toBeConfirmedCustomerList.size(); i++) {
            Customer iCustomer = toBeConfirmedCustomerList.get(i);
            if (repetitiveCustomers.contains(iCustomer)) {
                continue;
            }
            boolean isRepetitive = false;
            for (int j = i + 1; j < toBeConfirmedCustomerList.size(); j++) {
                Customer jCustomer = toBeConfirmedCustomerList.get(j);
                if (repetitiveCustomers.contains(jCustomer)) {
                    continue;
                }
                if (Boolean.TRUE.equals(matchingLogic.test(iCustomer, jCustomer))) {
                    jCustomer.setMatchingLogic(matchingLogic.matchingLogicName());
                    repetitiveCustomers.add(jCustomer);
                    isRepetitive = true;
                } else {
                    toBeConfirmedCustomers.add(jCustomer);
                }

            }
            if (isRepetitive) {
                iCustomer.setMatchingLogic(matchingLogic.matchingLogicName());
                repetitiveCustomers.add(iCustomer);
            } else {
                toBeConfirmedCustomers.add(iCustomer);
            }
        }
        return toBeConfirmedCustomers;
    }

    interface MyMatchingLogic {
        String matchingLogicName();

        Boolean test(Customer one, Customer another);
    }
}
