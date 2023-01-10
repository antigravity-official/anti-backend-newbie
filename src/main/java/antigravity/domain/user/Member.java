package antigravity.domain.user;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Where(clause = "deleted_at = NULL")
    private List<ProductLike> productLikes = new ArrayList<>();


    public boolean isLiked(Long productId) {
        return productLikes.stream()
            .anyMatch(p -> p.getProductId().equals(productId));
    }

    public void like(Long productId) {
        productLikes.add(new ProductLike(productId, this.id));
    }
}
