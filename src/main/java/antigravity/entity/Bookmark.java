package antigravity.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Bookmark(Long id, Long productId, User user) {
        this.id = id;
        this.productId = productId;
        this.user = user;
    }

    public static Bookmark bookmarkBuilder(Long productId, User user){
        return Bookmark.builder()
                .productId(productId)
                .user(user)
                .build();
    }
}
