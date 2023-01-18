package antigravity.entity;

import antigravity.repository.UserRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Builder
//@ToString
//@Getter
@Entity
public class Basket {

    /********************************* PK 필드 *********************************/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    /********************************* PK가 아닌 필드 *********************************/
    @Column(nullable = false)
    private Boolean liked; // 필요한 경우 찜한 상품임을 표시 (찜 여부)




    /********************************* 연관관계 매핑 *********************************/
    // TODO: product id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false)
    private Product product;

//
//    public User getUser() {
//        return user;
//    }


    /********************************* 생성 메소드 **********************************/
    public static Basket choiceProduct(Boolean liked, Product product, User user) {


        Basket basket = Basket.builder()
                .liked(liked)
                .product(product)
                .user(user)
                .build();

        return basket;
    }
}