package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Builder
@ToString
@Entity
public class Liked {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private Long userId;

    @Column(name = "product_id")
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    private Long productId;
}
