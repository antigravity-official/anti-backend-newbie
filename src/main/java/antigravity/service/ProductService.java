package antigravity.service;


import javax.validation.Valid;

import antigravity.dto.LikedDto;
import antigravity.dto.UserDto;
import antigravity.entity.Liked;

public interface ProductService {

	/**
	 * 이미 찜한 상품인지 조회하기
	 * @param likedDto 
	 * @param userDto
	 * @return
	 */
	public int isLiked(@Valid LikedDto likedDto, UserDto userDto);

	/**
	 * 찜상품 등록
	 * @param likedDto -찜하기 정보
	 * @param userDto -회원정보 
	 * @return - insert 결과
	 */
	public int resisterLiked(@Valid LikedDto likedDto, UserDto userDto);

	/**
	 * 찜하기 등록되면 조회수 상승
	 * @param likedDto -상품 정보
	 * @param userDto -유저 정보
	 */
	public void increaseViews(@Valid LikedDto likedDto, UserDto userDto);



}
