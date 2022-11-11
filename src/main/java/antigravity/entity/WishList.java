package antigravity.entity;

import antigravity.enums.Like;
import lombok.Getter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
public class WishList {

    @Id @GeneratedValue
    @Column(name = "wishList_id")
    private Long id;

    @Column(name = "liked")
    @Enumerated(EnumType.STRING)
    private Like liked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void saveWishList(Optional<User> user, Optional<Product> product) {
        this.liked = Like.TRUE;
        user.ifPresent(u -> {
            this.user = u;
        });
        product.ifPresent(p -> {
            this.product = p;
        });
    }

    public void cancelWishList() {
        this.liked = Like.FALSE;
    }
}
