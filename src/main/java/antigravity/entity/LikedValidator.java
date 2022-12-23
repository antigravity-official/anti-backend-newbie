package antigravity.entity;

import antigravity.payload.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;

import static antigravity.payload.ProductConstants.*;

public class LikedValidator {

    public static void deletedMember(Member member) {
        if (isDeleted(member.getDeletedAt())) {
            // 탈퇴한 회원
            throw new BadRequestException(DELETED_MEMBER);
        }
    }

    public static void deletedProduct(Product product) {
        if (isDeleted(product.getDeletedAt())) {
            // 삭제된 상품
            throw new BadRequestException(DELETED_PRODUCT);
        }
    }

    private static boolean isDeleted(LocalDateTime entityDeletedAt) {
        return null != entityDeletedAt && entityDeletedAt.isBefore(LocalDateTime.now());
    }

    public static void alreadyLiked(List<View> likedView) {
        if (!likedView.isEmpty()) {
            // 찜 한 상품
            throw new BadRequestException(ALREADY_LIKED);
        }
    }
}
