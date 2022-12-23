package antigravity.repository;

import antigravity.entity.LikedStatus;
import antigravity.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 찜 상품만 조회
     *
     * @param memberId    사용자
     * @param likedStatus LIKED only
     * @param pageable    페이징
     */
    @Query("select p from Product p join View v on p.id = v.product.id where v.member.id = :memberId and v.likedStatus = :likedStatus")
    Page<Product> findAllJoinViewByMemberAndLikedStatus(Long memberId, LikedStatus likedStatus, Pageable pageable);

    /**
     * 찜 하지 않은 상품 조회
     * 사용자가 View.LIKED 를 등록하지 않은 데이터만 조회
     *
     * @param memberId 사용자
     * @param pageable 페이징
     */
    @Query("select p from Product p where p.id not in "
           + "(select v.product.id from View v where v.member.id = :memberId and v.likedStatus = 'LIKED')")
    Page<Product> findAllByNotContainsLikedViewMemberId(Long memberId, Pageable pageable);
}
