package antigravity.domain.product;

import antigravity.web.exception.InvalidLikeStatusException;

public enum LikeStatus {

    NONE, FALSE, TRUE;

    public static LikeStatus findStatus(String likeStatus) {
        if (likeStatus == null) {
            return LikeStatus.NONE;
        }

        try {
            return LikeStatus.valueOf(likeStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidLikeStatusException();
        }
    }

}
