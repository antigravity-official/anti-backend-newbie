package antigravity.service;

import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.entity.WishList;
import antigravity.enums.Like;
import antigravity.payload.CreateWishListRequest;
import antigravity.payload.ProductResponse;
import antigravity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final WishListRepositoryCustom wishListRepositoryCustom;

    private final ProductRepositoryCustom productRepositoryCustom;



    @Override
    @Transactional
    public Long save(CreateWishListRequest request) {

        Optional<User> findUser = userRepository.findById(request.getUser().getId());
        findUser.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 입니다."));

        Optional<Product> findProduct = productRepository.findById(request.getProduct().getId());
        findProduct.orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품 입니다."));

        Optional<WishList> findWishList = wishListRepository.findByUserIdAndProductId(findUser.get().getId(), findProduct.get().getId());
        findWishList.ifPresent(wishList -> {
            throw new IllegalArgumentException("이미 찜 한 상품 입니다.");
        });

        WishList wishList = new WishList();
        wishList.saveWishList(findUser, findProduct);
        WishList save = wishListRepository.save(wishList);

        Product product = findProduct.get();
        product.productTotalLikedIncrease();
        productRepository.save(product);
        return save.getId();
    }

    @Override
    public Page<ProductResponse> getPage(String userId, boolean liked, Pageable pageable) {

        List<Long> productIds = new ArrayList<>();

        if (liked) {
            return wishListRepositoryCustom.getPage(userId, pageable);
        } else {
            List<WishList> userWishList = wishListRepository.findByUserId(Long.valueOf(userId))
                    .stream().filter(u -> u.getLiked().equals(Like.TRUE)).collect(Collectors.toList());

            userWishList.forEach(w -> {
                productIds.add(w.getProduct().getId());
            });

            return productRepositoryCustom.getPage(productIds, pageable).map(p -> new ProductResponse().make(p));

        }
    }

}
