package antigravity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity @Getter @NoArgsConstructor
public class LikeProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long userId;

    private Long productId;
    public LikeProduct(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
