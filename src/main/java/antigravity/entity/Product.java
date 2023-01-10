package antigravity.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE product SET deletedAt = NOW() where id =?")
@Where(clause = "deleted_at is NULL")
@DynamicUpdate
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

	private Integer viewed;

	@BatchSize(size = 100)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductLike> productLikes;

	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public void increaseViewed() {
		this.viewed += 1;
	}

	public boolean isDeleted() {
		return deletedAt != null;
	}

	public Integer getTotalLiked() {
		return Math.toIntExact(this.productLikes.stream()
			.filter(p -> p.getLikeStatus().isLike())
			.count());
	}

	public boolean getLiked(User user) {
		for (ProductLike productLike : productLikes) {
			if (productLike.getUser().equals(user)) {
				return productLike.getLikeStatus().isLike();
			}
		}
		return false;
	}

	@PreUpdate
	private void updatedAt() {
		this.updatedAt = LocalDateTime.now();
	}

}
