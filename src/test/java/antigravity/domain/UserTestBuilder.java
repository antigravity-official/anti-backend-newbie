package antigravity.domain;

import antigravity.entity.LikedProduct;
import antigravity.entity.Product;
import antigravity.entity.User;

import java.math.BigDecimal;

public class UserTestBuilder {
    public static User createUser0() {
        User user = User.builder()
                .email("'user3@antigravity.kr'")
                .name("회원3")
                .build();

        return user;
    }
}
