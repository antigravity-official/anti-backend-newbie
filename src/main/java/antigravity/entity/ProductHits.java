package antigravity.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductHits {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_hits_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    private Long hit;

    public void increaseHit(Long count) {
        if( this.hit == null ) {
            this.hit = count;
        }
        this.hit += count;
    }
}
