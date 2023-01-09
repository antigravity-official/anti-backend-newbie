package antigravity.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 60)
	private String sku;

	@Column(length = 125, nullable = false)
	private String name;

	@Column(precision = 12, scale = 2, nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer quantity;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	@PreUpdate
	private void updatedAt() {
		this.updatedAt = LocalDateTime.now();
	}

	private void deleteAt() {
		this.deletedAt = LocalDateTime.now();
	}

}
