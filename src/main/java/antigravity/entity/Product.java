package antigravity.entity;

import lombok.Getter;
import lombok.Setter;

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

    @OneToMany(mappedBy = "user")
    private List<WishList> wishLists = new ArrayList<>();

}
