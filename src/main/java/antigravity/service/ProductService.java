package antigravity.service;

import antigravity.dto.ProductDTO;
import antigravity.entity.Liked;
import antigravity.entity.Product;
import antigravity.repository.LikedRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import antigravity.repository.ViewedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikedRepository likedRepository;
    @Autowired
    private ViewedRepository viewedRepository;

    public boolean isExist(Long id){
        boolean isExist = productRepository.existsById(id);
        return isExist;
    }

    //all product
    @Transactional
    public List<ProductDTO> getProductAll(Pageable pageable){
        List<Product> productEntityList = productRepository.findAll((Sort) pageable);
        List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
        for( Product product: productEntityList) {
            Long countLiked = likedRepository.countByProductId(product.getId());
            Long countViewed = viewedRepository.findById(product.getId()).get().getCountViewed();
            productDTOList.add(new ProductDTO(product.getId(), product.getSku(), product.getName(),
                    product.getPrice(), product.getQuantity(), null, countLiked.intValue(), countViewed.intValue(),
                    product.getCreatedAt(), product.getUpdatedAt()));
        }

        return productDTOList;
    }

    //찜하기
    @Transactional
    public boolean likedProduct(Long userId, Long productId){
        Product product = productRepository.findById(productId).get();
        List<Liked> likedAll = likedRepository.findAllByProductId(product);

        if(!likedAll.isEmpty()){
            for(Liked like : likedAll) {
                if (like.getUserId() == userId) {
                    return false;
                }
            }
        }
        Liked liked = Liked.builder().userId(userId).productId(productId).build();
        likedRepository.save(liked);
        return true;
    }

    public List<ProductDTO> getLikedProduct(Long userId, Pageable pageable) {
        List<Long> likedProductIdList = likedRepository.findProductIdByUserId(userId, pageable);
        List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
        for( Long productId: likedProductIdList) {
            Long countLiked = likedRepository.countByProductId(productId);
            Long countViewed = viewedRepository.findById(productId).get().getCountViewed();
            Product product = productRepository.findById(productId).get();
            productDTOList.add(new ProductDTO(productId, product.getSku(), product.getName(),
                    product.getPrice(), product.getQuantity(), true, countLiked.intValue(), countViewed.intValue(),
                    product.getCreatedAt(), product.getUpdatedAt()));
        }
        return productDTOList;

    }

    public List<ProductDTO> getUnlikedProduct(Long userId, Pageable pageable) {
        List<ProductDTO> likedProductList = getLikedProduct(userId, pageable);
        List<ProductDTO> allProductList = getProductAll(pageable);

        for(ProductDTO product: likedProductList){
            allProductList.remove(product);
        }
        return allProductList;
    }
}
