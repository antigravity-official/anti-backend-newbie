package antigravity.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Product SET deleted_at = NOW() WHERE id = ?")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductStatistics productStatistics;

    @OneToMany(mappedBy = "product")
    private List<LikedProduct> likedProducts;

    @Builder
    public Product(Long id, String sku, String name, BigDecimal price, Integer quantity) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductStatistics createProductStatistics() {
        ProductStatistics productStatistics = ProductStatistics.builder()
                .product(this)
                .build();
        this.productStatistics = productStatistics;
        return productStatistics;
    }

    public int countLike() {
        if (likedProducts == null) {
            return 0;
        } else {
            return this.likedProducts.size();
        }
    }
}
