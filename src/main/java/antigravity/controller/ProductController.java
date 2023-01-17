package antigravity.controller;


import antigravity.dto.ProductDTO;
import antigravity.entity.Viewed;
import antigravity.exception.CustomBadRequestException;
import antigravity.payload.PageResponse;
import antigravity.payload.ProductResponse;
import antigravity.repository.ViewedRepository;
import antigravity.service.ProductService;
import antigravity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ViewedRepository viewedRepository;


    // TODO 찜 상품 등록 API
    @PostMapping(value = "/products/liked/{productId}")
    public Object userLiked(HttpServletRequest request, @RequestHeader(name = "X-USER-ID") Long userId,
                            @RequestParam(name = "productId") Long productId) throws CustomBadRequestException{
        if(!userService.isExist(userId)){
            throw new CustomBadRequestException("존재하지 않는 계정입니다.");
        } else if (!productService.isExist(productId)) {
            throw new CustomBadRequestException("존재하지 않는 상품입니다.");
        }

        if(productService.likedProduct(userId, productId)){
            Long count = viewedRepository.findById(productId).get().getCountViewed();
            count ++;
            viewedRepository.save(Viewed.builder().product_id(productId).countViewed(count).build());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{productId}")
                    .build().toUri();
            return ResponseEntity.created(location).build();
        } else {
            return new CustomBadRequestException("이미 찜한 상품입니다.");
        }

    }

    // TODO 찜 상품 조회 API
    @GetMapping(value = "/products?liked?liked={boolean}&page={integer}&size={integer}")
    public List<ProductDTO> selectLikedProducts(@RequestParam(name = "liked") Boolean liked,
                                                @RequestHeader(name = "X-USER-ID") Long userId,
                                                @PageableDefault(page = 0, size = 10) Pageable pageable) throws CustomBadRequestException {

        List<ProductDTO> productDTOList = new ArrayList<>();
        if (liked == null){
            productDTOList = productService.getProductAll(pageable);
        } else if (liked) {
            productDTOList = productService.getLikedProduct(userId, pageable);
        } else {
            productDTOList = productService.getUnlikedProduct(userId, pageable);
        }
        return productDTOList;
    }

}
