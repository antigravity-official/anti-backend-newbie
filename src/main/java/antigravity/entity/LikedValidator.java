package antigravity.entity;

import antigravity.entity.dto.exception.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;

public class LikedValidator {

    public static void deletedMember(Member member) {
        if (isDeleted(member.getDeletedAt())) {
            // 탈퇴한 회원
            throw new BadRequestException("탈퇴한 회원입니다.");
        }
    }

    public static void deletedProduct(Product product) {
        if (isDeleted(product.getDeletedAt())) {
            // 삭제된 상품
            throw new BadRequestException("삭제된 상품입니다.");
        }
    }

    private static boolean isDeleted(LocalDateTime entityDeletedAt) {
        return null != entityDeletedAt && entityDeletedAt.isBefore(LocalDateTime.now());
    }

    public static void alreadyLiked(List<View> likedView) {
        if (!likedView.isEmpty()) {
            // 찜 한 상품
            throw new BadRequestException("이미 찜한 상품입니다.");
        }
    }
}
