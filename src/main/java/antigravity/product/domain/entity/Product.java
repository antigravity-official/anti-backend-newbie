package antigravity.product.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Builder
@ToString
public class Product {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long viewed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    void setViewed(Long viewed) {
        this.viewed = viewed;
    }
}
