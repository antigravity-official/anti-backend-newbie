package antigravity.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints= {
                @UniqueConstraint(name = "liked_unique", columnNames= {"user_id","product_id"})})
@Entity
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
