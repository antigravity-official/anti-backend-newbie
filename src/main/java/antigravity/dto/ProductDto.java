package antigravity.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import antigravity.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
	private Long views;
    
	
	public Product toEntitiy() {
		return Product.builder()
				.id(id)
				.sku(sku)
				.name(name)
				.price(price)
				.quantity(quantity)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.deletedAt(deletedAt)
				.build();
	}
	
	
}
