package antigravity.payload;

import antigravity.entity.Product;
import antigravity.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWishListRequest {

    private User user;
    private Product product;
}
