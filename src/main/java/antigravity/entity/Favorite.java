package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
public class Favorite {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
