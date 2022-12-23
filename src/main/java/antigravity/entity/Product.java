package antigravity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Table(name = "product")
@Entity
@ToString(exclude = "viewList")
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String sku;
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private Integer quantity;
    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Column
    private LocalDateTime deletedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product",
            cascade = CascadeType.ALL)
    private List<View> viewList = new ArrayList<>();


    public int totalLiked() {
        return (int) this.viewList.stream()
                                  .filter(f -> f.getLikedStatus().liked())
                                  .count();
    }

    public LikedStatus memberMatchLikedStatus(Long memberId) {
        return this.viewList.stream().filter(f -> f.getMember().getId().equals(memberId))
                            .map(View::getLikedStatus)
                            .findAny()
                            .orElse(LikedStatus.NONE);
    }
}
