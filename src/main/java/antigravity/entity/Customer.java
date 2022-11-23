package antigravity.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false)
    private String email;

    @Column(length = 45, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "customer")
    private List<LikedProduct> likedProducts = new ArrayList<>();

    @Builder
    public Customer(Long id,
                    String email,
                    String name,
                    LocalDateTime createdAt,
                    LocalDateTime deletedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public void addLikeProducts(LikedProduct likedProduct) {
        likedProducts.add(likedProduct);
    }
}
