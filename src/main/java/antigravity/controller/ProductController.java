package antigravity.controller;

import antigravity.domain.repository.ProductRepository;
import antigravity.dto.MsgDto;
import antigravity.dto.ProductResponseDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="상품 조회 REST API ")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductRepository productRepository;

    // TODO 찜 상품 조회 API
    @ApiOperation(value = "찜 상품 조회 API", notes = "찜 여부 확인후 페이징된 상품 조회하는 api ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "liked", dataType = "Long", paramType = "query", value = "상품 고유 번호",required = false),
            @ApiImplicitParam(name = "X-USER-ID", dataType = "Long", paramType = "header", value = "유저 고유 번호"),
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "몇 페이지인지"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "페이지당 몇개 조회하는지")
    })
    @ApiResponses(
            @ApiResponse(code = 200, message = "API 정상 작동",response = MsgDto.class))
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDto>> getWants(@RequestParam(required = false)Boolean liked, @RequestHeader(name = "X-USER-ID") Long userId,  Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.getWantProducts(liked, userId,pageable));
    }
}
