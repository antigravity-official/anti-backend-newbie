package antigravity.controller;

import antigravity.dto.MsgDto;
import antigravity.service.WantService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="상품 찜 등록 REST API ")
@RequiredArgsConstructor
@RestController
public class WantController {

    private final WantService wantService;
    // TODO 찜 상품 등록 API
    @ApiOperation(value = "상품 찜 등록 API", notes = "상품 선택시 찜 등록해주는 api ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", dataType = "Long", paramType = "path", value = "상품 고유 번호"),
            @ApiImplicitParam(name = "X-USER-ID", dataType = "Long", paramType = "header", value = "유저 고유 번호"),
    })
    @ApiResponses(
            @ApiResponse(code = 200, message = "API 정상 작동",response = MsgDto.class))
    @PostMapping("/products/liked/{productId}")
    public ResponseEntity<MsgDto> CreateWant(@PathVariable Long productId, @RequestHeader(name = "X-USER-ID") Long userId){
        return wantService.createWantItems(productId,userId);
    }
}
