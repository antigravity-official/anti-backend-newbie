package antigravity.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antigravity.dto.LikedDto;
import antigravity.entity.Liked;
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
	public int resisterLiked(@Valid LikedDto likedDto) {
		log.debug("resiterLiked");
		
		int insertResult = productRepo.insertLiked(likedDto);
		log.info("insertLiked {}",insertResult);
		return insertResult;
	}
//	@Override
//	public List<Product> getList() {
//		log.debug("getList");
//		List<Product> productList = produrctRepo.selectProduct();
//		log.info("서비스 영역 productList {}",productList);
//		return productList;
//	}
//	
//	@Override
//	public List<Liked> getLikedList(Long userId) {
//		log.debug("getLikedList");
//		List<Liked> isLiked = productRepo.selectLikedById(userId);
//		
//		return isLiked;
//	}
//	

}
