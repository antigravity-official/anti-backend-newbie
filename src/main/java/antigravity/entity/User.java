package antigravity.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
//@ToString
//@Getter
@Entity
public class User {


    /********************************* PK 필드 *********************************/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /********************************* PK가 아닌 필드 *********************************/
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime deletedAt;


    /********************************* 연관관계 매핑 *********************************/

    @Singular
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private final List<Basket> baskets = new ArrayList<>();

}
