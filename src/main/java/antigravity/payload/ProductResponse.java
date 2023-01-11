package antigravity.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class ProductResponse {

    @JsonProperty
    private Long id; // 상품아이디

    @JsonProperty
    private String sku; // 상품 고유값

    @JsonProperty
    private String name; // 상품명

    @JsonProperty
    private BigDecimal price; // 가격

    @JsonProperty
    private Integer quantity; // 재고수량

    @JsonProperty
    private Boolean liked; // 필요한 경우 찜한 상품임을 표시 (찜 여부)

    @JsonProperty
    private Integer totalLiked; // 상품이 받은 모든 찜 개수

    @JsonProperty
    private Integer viewed; // 상품 조회 수

    @JsonProperty
    private LocalDateTime createdAt; // 상품 생성일시

    @JsonProperty
    private LocalDateTime updatedAt; // 상품 수정일시

}
