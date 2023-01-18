package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

//@Builder
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
}
