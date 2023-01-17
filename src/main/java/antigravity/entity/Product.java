package antigravity.entity;

import lombok.*;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
