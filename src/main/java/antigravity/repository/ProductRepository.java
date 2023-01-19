package antigravity.repository;


import javax.validation.Valid;

import antigravity.dto.LikedDto;

public interface ProductRepository {



	public int insertLiked(@Valid LikedDto likedDto);

}
