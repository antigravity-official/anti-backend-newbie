package antigravity.common.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PageRequestDto {
	@NotNull
	@Positive
	protected Integer page;
	@NotNull
	@Positive
	protected Integer size;

	protected Pageable of() {
		return PageRequest.of(page - 1, size);
	}
}
