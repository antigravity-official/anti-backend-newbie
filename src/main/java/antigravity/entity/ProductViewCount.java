package antigravity.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductViewCount {
    @Id
    private Long id;

    private Long count;

    @Builder
    public ProductViewCount(Long id) {
        this.id = id;
        count = 0L;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
