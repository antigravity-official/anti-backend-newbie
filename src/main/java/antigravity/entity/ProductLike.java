package antigravity.entity;

import antigravity.exception.ProductCommonException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_like_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private ProductLike(User user, Product product) {
        if( user == null ) {
            throw new ProductCommonException("[INVALID] Product is empty, try again");
        }
        if( product == null ) {
            throw new ProductCommonException("[INVALID] User is empty, try again");
        }
        this.user = user;
        this.product = product;
    }

    public static ProductLike newInstance(User user, Product product) {
        return new ProductLike(user, product);
    }
}
