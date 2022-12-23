package antigravity.entity.dto;

import antigravity.entity.LikedStatus;
import antigravity.entity.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public class LikedDto {

    public static class Create {

        @Getter
        @NoArgsConstructor
        public static class Request{
            private LikedStatus likedStatus;

            public Request(LikedStatus likedStatus) {
                // 현재 스펙은 항상 찜 등록에만 호출, 조회 수 체크 스펙 변경 후 default NONE
                this.likedStatus = null == likedStatus ? LikedStatus.LIKED : likedStatus;
            }
        }
        @AllArgsConstructor
        @ToString
        @Getter
        public static class Condition {
            private Long productId;
            private Long memberId;
            private LikedStatus likedStatus;

            public Condition(Long productId, Long memberId, Create.Request request) {
                this.productId = productId;
                this.memberId = memberId;
                this.likedStatus = request == null ? null : request.getLikedStatus();
            }
        }

        @ToString
        @Getter
        @RequiredArgsConstructor
        public static class Response {
            private final Long likedId;
            private final Long productId;
            private final Long memberId;
            private final LikedStatus likedStatus;

            public Response(View view) {
                this.likedId = view.getId();
                this.productId = view.getProduct().getId();
                this.memberId = view.getMember().getId();
                this.likedStatus = view.getLikedStatus();
            }
        }
    }
}
