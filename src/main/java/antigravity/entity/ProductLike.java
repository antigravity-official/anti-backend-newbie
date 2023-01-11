package antigravity.entity;

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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "product_like")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE product_like SET deletedAt = NOW() where id =?")
@Where(clause = "deleted_at is NULL")
@DynamicUpdate
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

	public static ProductLike from(Product product, User user) {
		return new ProductLike(product, user);
	}

	private ProductLike(Product product, User user) {
		this.product = product;
		this.user = user;
		this.likeStatus = LikeStatus.LIKE;
	}

}
