package antigravity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

		//테스트
	@GetMapping(value="/hello")
	public String hello() {
		
		return "hello";
	}
	
    // TODO 찜 상품 등록 API

    // TODO 찜 상품 조회 API

}
