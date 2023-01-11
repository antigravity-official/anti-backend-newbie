package antigravity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "liked_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikedProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column
	private boolean isLiked;

	public LikedProduct(User user, Product product) {
		this.user = user;
		this.product = product;
		this.isLiked = true;
		this.product.increaseViews();
		this.product.getProductLiked().add(this);
		this.user.getLikedProducts().add(this);
	}

	public void changeLikeStatus() {
		this.isLiked = !isLiked;
	}
}
