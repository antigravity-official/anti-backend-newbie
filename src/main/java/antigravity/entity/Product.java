package antigravity.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Product SET deleted_at = NOW() WHERE id = ?")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 60)
    private String sku;

    @Column(length = 60)
    private String name;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    @Column
    private Integer quantity;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

/*
    public static Product createProduct(Long id, String sku, String name, BigDecimal price, Integer quantity) {
        return Product.builder()
                .id(id)
                .sku(sku)
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }*/

    @Builder
    public Product(Long id, String sku, String name, BigDecimal price, Integer quantity) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
