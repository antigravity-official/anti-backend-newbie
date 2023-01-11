package antigravity.router;

import antigravity.payload.ProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductResponseRouter {
    private final List<ProductResponseMaker> makers;

    public List<ProductResponse> getMatchList(Boolean liked, Long memberId, Pageable pageable) {
        return makers.stream()
                .filter(maker -> maker.isSupport(liked))
                .findFirst()
                .map(maker -> maker.getProductList(memberId, pageable))
                .get();
    }
}
