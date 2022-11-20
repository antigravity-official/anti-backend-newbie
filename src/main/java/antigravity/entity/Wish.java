package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
public class Wish {

    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;
}
