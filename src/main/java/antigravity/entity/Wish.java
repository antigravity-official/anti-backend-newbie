package antigravity.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="wish")
public class Wish {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="wish_id")
    private Long id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @Column
    private boolean isWish;

    @Builder
    public Wish(Product product, Users users) {
        this.product = product;
        this.product.getWishList().add(this);
        this.product.addView();
        this.users = users;
        this.users.getWishList().add(this);
        isWish = true;
    }

    public void changeWish(){
        isWish = !isWish;
    }
}
