package antigravity.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@Table(name = "liked_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Customer customer;

    /**
     * 좋아요한 상품을 생성한다..
     * @param product 좋아요한 상품
     * @param customer 좋아요한 고객
     * @return 좋아요한 상품
     */
    public static LikedProduct createLikedProduct(Product product, Customer customer) {
        return LikedProduct.builder()
                .product(product)
                .customer(customer)
                .build();
    }

    @Builder
    private LikedProduct(Product product, Customer customer) {
        this.product = product;
        this.customer = customer;
    }
}
