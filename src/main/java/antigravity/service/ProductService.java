package antigravity.service;


import javax.validation.Valid;

import antigravity.dto.LikedDto;

public interface ProductService {


	/**
	 * 찜상품 등록
	 * @param likedDto -찜하기 정보
	 * @return - insert 결과
	 */
	public int resisterLiked(@Valid LikedDto likedDto);


}
