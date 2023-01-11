package antigravity.controller;

import antigravity.common.MemberID;
import antigravity.dto.LikeHistorySearchCondition;
import antigravity.exception.IllegalParameterException;
import antigravity.payload.ProductResponse;
import antigravity.service.LikeHistoryService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final LikeHistoryService likeHistoryService;

    @PostMapping("/liked/{productId}")
    public ResponseEntity<Void> addLikeHistory(@PathVariable Long productId, @MemberID Long memberId) {
        likeHistoryService.addNotDuplicatedLikeHistory(productId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/liked")
    public ResponseEntity<List<ProductResponse>> getLikeHistoryList(@Valid LikeHistorySearchCondition condition,
                                                                    BindingResult bindingResult,
                                                                    @MemberID Long memberId) {
        if (bindingResult.hasErrors()) {
            throw new IllegalParameterException();
        }
        return ResponseEntity.ok().body(likeHistoryService.getLikeHistoryList(condition, memberId));
    }

}
