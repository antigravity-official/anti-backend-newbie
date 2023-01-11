package antigravity.controller;

import antigravity.repository.favoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final favoriteRepository fr;

    // TODO 찜 상품 등록 API
    @PostMapping(value = "/products/liked/{productId}")
    public void registerFavorite(@RequestHeader("X-USER-ID") int userId, @PathVariable int productId){
          fr.saveFavorite(userId,productId);
          fr.increaseViewcount(productId);
    }

    // TODO 찜 상품 조회 API

}
