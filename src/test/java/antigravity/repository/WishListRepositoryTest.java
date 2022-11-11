package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.entity.WishList;
import antigravity.enums.Like;
import antigravity.payload.CreateWishListRequest;
import antigravity.service.WishListService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class WishListRepositoryTest {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WishListService wishListService;

    @Test
    @Transactional
    @DisplayName("찜 상품 등록")
    @Rollback(false)
    void postWishList() {
        //given
        List<Product> products = productRepository.findAll();
        List<User> users = userRepository.findAll();
        Product product = products.get(1);
        User user = users.get(3);

        CreateWishListRequest createWishListRequest = new CreateWishListRequest();
        createWishListRequest.setProduct(product);
        createWishListRequest.setUser(user);

        //when
        Long saveId = wishListService.save(createWishListRequest);
        Optional<WishList> findWishList = wishListRepository.findById(saveId);

        //then
        Assertions.assertThat(findWishList.get().getId()).isEqualTo(7);
    }

    @Test
    @Transactional
    @DisplayName("찜 상품 취소하기")
    @Rollback(false)
    void cancelWishList() {

        //given
        Optional<WishList> findWishList = wishListRepository.findByUserIdAndProductId(1L, 1L);

        //when
        findWishList.ifPresent(wishList -> {
            wishList.cancelWishList();
            wishListRepository.save(wishList);

            //then
            Assertions.assertThat(wishList.getLiked()).isEqualTo(Like.FALSE);
        });

    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("회원이 찜한 상품 조회")
    void getWishList() {

        Optional<User> findUser = userRepository.findById(1L);
        List<WishList> userWishList = wishListRepository.findByUserId(findUser.get().getId());

        //then
        Assertions.assertThat(userWishList).contains(userWishList.get(0));

    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("회원이 찜한 상품을 제외한 상품 조회")
    void getFalseWishList() {

        Optional<User> user = userRepository.findById(1L);
        List<WishList> userWishList = wishListRepository.findByUserId(user.get().getId());
        List<Long> productIds = new ArrayList<>();

        userWishList.forEach(w -> {
            productIds.add(w.getProduct().getId());
        });

        List<Product> byProductIdNotIn = productRepository.findByIdNotIn(productIds);
        for (Product product : byProductIdNotIn) {
            System.out.println(product.getId());
        }

    }

}