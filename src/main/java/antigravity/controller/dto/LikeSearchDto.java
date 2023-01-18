package antigravity.controller.dto;

import org.springframework.data.domain.Pageable;

import antigravity.common.dto.PageRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeSearchDto extends PageRequestDto {
	private Boolean isLiked;

	public LikeSearchDto(Integer page,
		Integer size,
		Boolean isLiked) {
		super(page, size);
		this.isLiked = isLiked;
	}

	public Pageable of() {
		return super.of();
	}
}
