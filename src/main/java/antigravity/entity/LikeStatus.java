package antigravity.entity;

public enum LikeStatus {

	UNLIKE, LIKE;

	public boolean isLike() {
		return this == LIKE;
	}
}
