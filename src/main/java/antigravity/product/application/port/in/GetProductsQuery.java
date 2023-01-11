package antigravity.product.application.port.in;

import antigravity.product.application.port.in.dto.GetProductsOutput;
import antigravity.product.application.port.in.dto.GetProductsInput;
import org.springframework.data.domain.Page;

public interface GetProductsQuery {

	Page<GetProductsOutput> getProducts(GetProductsInput input);

}
