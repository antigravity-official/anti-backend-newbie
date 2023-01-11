package antigravity.web.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductListResponse {

    private List<ProductResponse> products;
    private PagingInfo pagingInfo;

    public ProductListResponse(List<ProductResponse> products, PagingInfo pagingInfo) {
        this.products = products;
        this.pagingInfo = pagingInfo;
    }

}
