package antigravity.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter @NoArgsConstructor
public class LikeProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private Long userId;
    @Column
    private Long productId;
    public LikeProduct(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
