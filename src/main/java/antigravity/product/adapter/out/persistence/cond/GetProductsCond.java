package antigravity.product.adapter.out.persistence.cond;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductsCond {

	private Long userId;
	private Boolean liked;

}
