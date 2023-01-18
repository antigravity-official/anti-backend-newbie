package antigravity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antigravity.entity.Product;
import antigravity.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	 private final ProductRepository productRepo;
	
	@Override
	public List<Product> getList() {
		log.debug("getList");
		List<Product> productList = productRepo.selectProduct();
		log.info("서비스 영역 productList {}",productList);
		return productList;
	}

}
