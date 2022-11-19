package antigravity.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikePostRequestDto {

    private Long userId;
    private Long productId;
}
