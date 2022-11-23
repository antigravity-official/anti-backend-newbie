package antigravity.domain;

import antigravity.entity.Customer;

public class UserTestBuilder {
    public static Customer createUser0() {
        Customer customer = Customer.builder()
                .email("'user3@antigravity.kr'")
                .name("회원3")
                .build();

        return customer;
    }
}
