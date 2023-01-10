package antigravity.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 다른곳에서 해당 엔티티를 직접생성 못하게
public class Product {

    @Id @GeneratedValue
    @Column(name="product_id")
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private int view;

    @OneToMany(mappedBy = "product")
    private List<Wish> wishList = new ArrayList<>();

    public void addView(){
        this.view++;
    }

    @Builder
    public Product(String sku, String name, BigDecimal price, int quantity){
        this.sku=sku;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }
}
