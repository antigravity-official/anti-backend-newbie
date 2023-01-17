package antigravity.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
public class Viewed {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne(targetEntity = Product.class)
    private Long product_id;

    @Column(name = "count_viewed")
    private Long countViewed;
}
