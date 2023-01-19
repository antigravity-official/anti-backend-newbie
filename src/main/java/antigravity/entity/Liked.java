package antigravity.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class Liked {
	private Long LikedNo;
	private Long productId;
	private Long userId;
	
	public Liked toEntitiy() {
		return Liked.builder()
				.LikedNo(LikedNo)
				.productId(productId)
				.userId(userId)
				.build();
	}
}
