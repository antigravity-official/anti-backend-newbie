package antigravity.entity;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
public class ProductInfo {


    /********************************* PK 필드 *********************************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    /********************************* PK가 아닌 필드 *********************************/
    @Column(nullable = false)
    private Integer totalLiked; // 상품이 받은 모든 찜 개수

    @Column(nullable = false)
    private Integer viewed; // 상품 조회



    /********************************* 연관관계 매핑 *********************************/
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /********************************* 생성 메소드 **********************************/
    public static ProductInfo changeViewProduct(Integer totalLiked, Integer viewed, Product product) {

        ProductInfo productInfo = ProductInfo.builder()
                .totalLiked(totalLiked)
                .viewed(viewed)
                .product(product)
                .build();

        return productInfo;
    }
}
