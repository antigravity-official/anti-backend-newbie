package antigravity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import antigravity.entity.Product;
import antigravity.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ProductController {

	//서비스 객체 
	@Autowired 
	private final ProductService productService;
	
	// TODO 찜 상품 등록 API
	@GetMapping(value="/products/liked")
	public void likdProduct(Model model) {
		log.debug("상품등록을 위한 페이지");
		List<Product> productList = productService.getList();
		log.info("상품 리스트 - {}",productList);
		
		model.addAttribute("productList",productList);

	}

	

    // TODO 찜 상품 조회 API

}
