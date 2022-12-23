package antigravity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "view")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class View extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;
    @Column
    @Enumerated(EnumType.STRING)
    private LikedStatus likedStatus;
    @Column
    private LocalDateTime deletedAt;

    public View(Product product, Member member, LikedStatus likedStatus) {
        this.product = product;
        this.member = member;
        this.likedStatus = likedStatus;
        this.product.getViewList().add(this);
    }

}
