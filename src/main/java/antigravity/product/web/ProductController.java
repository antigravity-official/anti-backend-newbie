package antigravity.product.web;

import antigravity.product.service.ProductService;
import antigravity.product.web.dto.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("products/liked/")
public class ProductController {
    private ProductService productService;

    // TODO 찜 상품 등록 API
    @PostMapping("{productId}")
    public ResponseEntity<Long> postDipProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
    // TODO 찜 상품 조회 API
    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getDipProductList(@PathParam("liked") boolean liked, Pageable pageable) {
        List<ProductResponse> productList = productService.findProductList(liked, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
}
