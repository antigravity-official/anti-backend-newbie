package antigravity.product.application.service;

import antigravity.product.application.port.in.dto.GetProductsOutput;
import antigravity.product.adapter.out.persistence.cond.GetProductsCond;
import antigravity.product.application.port.in.dto.GetProductsInput;
import antigravity.product.application.port.in.GetProductsQuery;
import antigravity.product.application.port.out.LoadProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GetProductsService implements GetProductsQuery {

	private final LoadProductPort loadProductPort;

	@Transactional(readOnly = true)
	@Override
	public Page<GetProductsOutput> getProducts(GetProductsInput input) {
		return loadProductPort.getPagingProducts(GetProductsCond.builder()
				.userId(input.getUserId())
				.liked(input.getLiked())
				.build(),
			input.getPageable());
	}
}
