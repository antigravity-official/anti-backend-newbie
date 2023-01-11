package antigravity.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "product_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_like_status")
    private LikeStatus likeStatus;

    @Builder
    public ProductLike(User user, Product product, LikeStatus likeStatus) {
        this.user = user;
        this.product = product;
        this.likeStatus = LikeStatus.LIKE;
    }
}
