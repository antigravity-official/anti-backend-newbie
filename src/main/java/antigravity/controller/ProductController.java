package antigravity.controller;

import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.entity.Wanted;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class ProductController {

    ProductResponse productResponse;

    @Autowired
    private ProductRepository productRepository;


    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public ProductResponse Wanted_Products(@RequestBody Product product, String email){
        Product result = productRepository.findById(product.getId());
        int viewed = result.getViewed() + 1;
        productResponse.setId(result.getId());
        productResponse.setViewed(viewed);
        productResponse.setSku(result.getSku());

        productRepository.viewedUpdate(productResponse);
        productRepository.ImportWanted(productResponse.getSku(), email);

        return productResponse;
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/products")
   public ArrayList<ProductResponse> ShowWantedPorducts(User user, Boolean Liked){
        ArrayList<ProductResponse> result = null;

        result = productRepository.Wanted_List(user.getEmail(), Liked);

        return result;
    }
}
