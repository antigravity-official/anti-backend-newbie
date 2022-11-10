package antigravity.entity;

import antigravity.enums.Like;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class WishList {

    @Id @GeneratedValue
    @Column(name = "wishList_id")
    private Long id;

    @Column(name = "liked")
    @Enumerated(EnumType.STRING)
    private Like liked;

    private Integer totalLiked;
    private Integer viewed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
