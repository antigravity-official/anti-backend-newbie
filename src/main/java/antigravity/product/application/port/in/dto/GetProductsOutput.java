package antigravity.product.application.port.in.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetProductsOutput {

    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Boolean liked;
    private Integer totalLiked;
    private Integer viewed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
