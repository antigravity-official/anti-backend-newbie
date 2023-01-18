package antigravity.repository;

import java.util.List;

import antigravity.entity.Product;

public interface ProductRepository {

	/**
	 * 상품목록 조회하기
	 * @return- 상품 목록 
	 */
	public List<Product> selectProduct();

}
