package antigravity.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	@Column(length = 60)
	private String sku;

	@Column(length = 125, nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(name = "product_views")
	private int views;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Builder
	public Product(String sku, String name, BigDecimal price, int views, Integer quantity) {
		this.sku = sku;
		this.name = name;
		this.price = price;
		this.views = views;
		this.quantity = quantity;
	}

	public void increaseViews() {
		this.views += 1;
	}

}
