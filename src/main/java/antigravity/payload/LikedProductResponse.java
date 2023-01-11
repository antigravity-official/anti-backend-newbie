package antigravity.payload;

import lombok.Getter;

@Getter
public class LikedProductResponse {
	private final Long userId;
	private final Long productId;
	private final String registeredCompletedMsg;

	public LikedProductResponse(Long userId, Long productId) {
		this.userId = userId;
		this.productId = productId;
		this.registeredCompletedMsg = "해당 제품이 찜 목록에 추가되었습니다.";
	}
}
