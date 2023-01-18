package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class Basket {

    private Boolean liked; // 필요한 경우 찜한 상품임을 표시 (찜 여부)
}
