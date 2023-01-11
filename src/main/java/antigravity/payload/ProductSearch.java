package antigravity.payload;

import static java.lang.Math.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductSearch {

	private static final int MAX_SIZE = 1000;


	private Boolean liked;
	@Builder.Default
	private Integer page = 1;
	@Builder.Default
	private Integer size = 10;

	public long getOffset() {
		return (long)(max(1, page) - 1) * min(size, MAX_SIZE);
	}
}
