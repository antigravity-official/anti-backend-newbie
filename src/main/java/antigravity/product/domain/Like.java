package antigravity.product.domain;

import antigravity.product.errors.CustomException;
import antigravity.product.errors.ErrorCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "likes",
	uniqueConstraints = {
		@UniqueConstraint(name = "unique_user_product"
			, columnNames = {"user_id", "product_id"})
	})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Entity
public class Like extends BaseTimeEntity{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(nullable = false, name = "user_id")
	private Long userId;

	private boolean liked;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	private Product product;

	public static Like create(Long userId) {
		return Like.builder()
			.userId(userId)
			.build();
	}

	public void likeProduct(Product product) {
		if (liked) {
			throw new CustomException(ErrorCode.ALREADY_LIKED_PRODUCT);
		}

		this.liked = true;
		this.product = product;
	}


}
