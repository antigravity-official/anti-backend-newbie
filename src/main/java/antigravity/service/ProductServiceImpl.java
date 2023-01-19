package antigravity.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antigravity.dto.LikedDto;
import antigravity.dto.UserDto;
import antigravity.entity.Liked;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	 private final ProductRepository productRepo;
	
	
	@Override
	public int isLiked(@Valid LikedDto likedDto, UserDto userDto) {
		log.debug("isLiked");
		
		int resultIsLiked = productRepo.selectCnt(likedDto,userDto);
		log.info("찜한 상품 확인 {}",resultIsLiked);
		return resultIsLiked;
	}
	
	@Override
	public int resisterLiked(@Valid LikedDto likedDto , UserDto userDto) {
		log.debug("resiterLiked");
		
		int insertResult = productRepo.insertLiked(likedDto,userDto);
		log.info("insertLiked {}",insertResult);
		return insertResult;
	}
	
	@Override
	public void increaseViews(@Valid LikedDto likedDto, UserDto userDto) {

		log.debug("increaseViews");
		productRepo.updateViews(likedDto,userDto);
		
	}

}
