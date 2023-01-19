package antigravity.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import antigravity.dto.LikedDto;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ProductController {

	//서비스 객체 
	@Autowired 
	private final ProductService productService;

//	
	
	// TODO 찜 상품 등록 API
	@PostMapping("/products/liked/{productId}")
	public ResponseEntity<String> registedLiked (@Valid @RequestBody LikedDto likedDto){
		log.debug("찜 목록 등록");
		
		
		int result = productService.resisterLiked(likedDto);
		
		if(result >0) {
			log.info("찜하기 등록 {}",result);
			return ResponseEntity.status(HttpStatus.OK).body("liked Resitered");
		}
		log.info("찜하기 등록 {}",result);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail to resister Liked") ;
		
	}
	
    // TODO 찜 상품 조회 API

}
