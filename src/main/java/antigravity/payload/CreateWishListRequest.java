package antigravity.payload;

import antigravity.entity.Product;
import antigravity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateWishListRequest {

    private User user;
    private Product product;
}
