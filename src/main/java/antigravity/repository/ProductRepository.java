package antigravity.repository;


import javax.validation.Valid;

import antigravity.dto.LikedDto;
import antigravity.dto.UserDto;
import antigravity.entity.Liked;

public interface ProductRepository {

	/**
	 * 찜한 상품 확인하기 
	 * @param likedDto
	 * @param userDto
	 * @return
	 */
	public int selectCnt(@Valid LikedDto likedDto, UserDto userDto);

	/**
	 * 찜상품 등록하기
	 * @param likedDto -찜한 상품
	 * @param userDto
	 * @return
	 */
	public int insertLiked(@Valid LikedDto likedDto, UserDto userDto);
	
	/**
	 * 조회수 업데이트
	 * @param likedDto
	 * @param userDto
	 */

	public void updateViews(@Valid LikedDto likedDto, UserDto userDto);


}
