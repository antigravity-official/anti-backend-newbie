package antigravity.service;

import java.util.List;

import antigravity.entity.Product;

public interface ProductService {

	/**
	 * 찜 상품 등록을 위한 전체 상품 목록 조회
	 * @return - 상품목록 
	 */
	public List<Product> getList();

}
