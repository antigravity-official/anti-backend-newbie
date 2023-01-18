package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@ToString
@Getter
public class Product {

    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Integer viewed;
    private Boolean Liked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
