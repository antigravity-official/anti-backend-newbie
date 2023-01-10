package antigravity.product.domain.entity;

import antigravity.util.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long viewed;
    private LocalDateTime deletedAt;
    @Builder
    public Product(String sku, String name, BigDecimal price, Integer quantity, LocalDateTime deletedAt) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.deletedAt = deletedAt;
    }
    void setViewed(Long viewed) {
        this.viewed = viewed;
    }

    @PrePersist
    public void prePersist() {
        this.viewed = this.viewed == null ? 0: this.viewed;
    }
}
