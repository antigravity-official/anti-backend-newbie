package antigravity.payload;

import antigravity.entity.Product;
import antigravity.entity.Wish;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponse {

    private Long id; // 상품아이디
    private String sku; // 상품 고유값
    private String name; // 상품명
    private BigDecimal price; // 가격
    private Integer quantity; // 재고수량
    private Boolean liked; // 필요한 경우 찜한 상품임을 표시 (찜 여부)
    private Integer totalLiked; // 상품이 받은 모든 찜 개수
    private Integer viewed; // 상품 조회 수
    private LocalDateTime createdAt; // 상품 생성일시
    private LocalDateTime updatedAt; // 상품 수정일시

    @JsonBackReference
    private List<Wish> wishList = new ArrayList<>();

    public ProductResponse(Product product, boolean liked) {
        id = product.getId();
        name = product.getName();
        price = product.getPrice();
        quantity = product.getQuantity();
        this.liked = liked;
        wishList = product.getWishList();
        viewed = product.getView();
        createdAt = product.getCreatedAt();
        updatedAt = product.getUpdatedAt();
    }

}
