package antigravity.domain;

import antigravity.common.exception.DuplicatedLikeException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString(exclude = {"product", "user"})
public class ProductLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private LocalDateTime deletedAt;

    public void recoverLiked() {
        if (this.deletedAt == null) {
            throw new DuplicatedLikeException();
        }
        this.deletedAt = null;
    }

    public static ProductLike of(Product product, User user) {
        return new ProductLike(null, product, user, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductLike that = (ProductLike) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
