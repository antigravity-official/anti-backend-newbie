package antigravity.domain.member;

import antigravity.domain.product.Product;
import antigravity.domain.product.ProductLike;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at is null")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductLike> productLikes = new ArrayList<>();

    public boolean likes(Long productId) {
        return productLikes.stream()
            .anyMatch(p -> p.getProduct().getId().equals(productId));
    }

    public void like(Product product) {
        ProductLike productLike = new ProductLike(product, this);
        this.productLikes.add(productLike);
        product.increaseViewCount();
    }
}
