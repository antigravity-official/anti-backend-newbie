package antigravity.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "product_like")
@NoArgsConstructor
@Entity
public class ProductLike extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "like_status")
	@Enumerated(EnumType.STRING)
	private LikeStatus likeStatus;

	private LocalDateTime deletedAt;

	public static ProductLike from(Product product, User user) {
		return new ProductLike(product, user);
	}

	private ProductLike(Product product, User user) {
		this.product = product;
		this.user = user;
		this.likeStatus = LikeStatus.LIKE;
	}

}
