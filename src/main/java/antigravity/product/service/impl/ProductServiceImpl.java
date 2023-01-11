package antigravity.product.service.impl;

import antigravity.global.exception.AntiException;
import antigravity.product.domain.dao.LikeProductDAO;
import antigravity.product.domain.dao.ProductDAO;
import antigravity.product.domain.entity.LikeProduct;
import antigravity.product.domain.entity.Product;
import antigravity.product.domain.repository.LikeProductRepository;
import antigravity.product.domain.repository.ProductRepository;
import antigravity.product.exception.ProductErrorCode;
import antigravity.product.service.LikeProductService;
import antigravity.product.service.ProductService;
import antigravity.product.web.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final LikeProductDAO likeProductDAO;
    private final ProductDAO productDAO;
    @Override
    public Page<ProductResponse> findProductList(Integer userId, Boolean liked, Pageable pageable) {
        // 모든 상품을 조회
        if (liked == null) {
            Page<Product> products = productDAO.findAllProduct(pageable);
            Map<Long, ProductResponse> productMap = new HashMap<>();

            //어자피 len(Product) >= len(dipProduct) 이므로 pageable 똑같이 써도 상관X
            likeProductDAO.findAllByUserId(userId, pageable).map(dipProduct ->
                    ProductResponse.createDipProduct(productDAO.findById(dipProduct.getProductId()), likeProductDAO.calculateTotalDip(dipProduct.getProductId())))
                    .forEach(productResponse -> productMap.put(productResponse.getId(), productResponse));
            return products.map(product -> productMap.getOrDefault(product.getId(), ProductResponse.createNotDipProduct(product, likeProductDAO.calculateTotalDip(product.getId()))));
        } else if (liked) { // 찜한 상품을 조회
            return likeProductDAO.findAllByUserId(userId,pageable).map(dipProduct ->
                    ProductResponse.createDipProduct(productDAO.findById(dipProduct.getProductId()), likeProductDAO.calculateTotalDip(dipProduct.getProductId())));
        } else { // 찜하지 않은 상품만 조회
            Page<LikeProduct> dipProducts = likeProductDAO.findAllByUserId(userId,pageable);
            List<Long> dipProductIds = dipProducts.stream().map(LikeProduct::getProductId).collect(Collectors.toList());
            return productDAO.findAllNotDipProduct(dipProductIds, pageable)
                    .map(notDipProduct -> ProductResponse.createNotDipProduct(notDipProduct, likeProductDAO.calculateTotalDip(notDipProduct.getId())));
        }
    }
}
