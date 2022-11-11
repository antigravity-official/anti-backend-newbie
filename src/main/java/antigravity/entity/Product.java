package antigravity.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Product extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    @ColumnDefault("0")
    private Integer totalLiked;

    @ColumnDefault("0")
    private Integer viewed;

    @OneToMany(mappedBy = "product")
    private List<WishList> wishLists = new ArrayList<>();

    public void productViewIncrease() {

        this.viewed += 1;
    }

    public void productTotalLikedIncrease() {

        this.totalLiked += 1;
    }
}
