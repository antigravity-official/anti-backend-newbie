package antigravity.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
@Table(name = "Member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String email;
    @Column
    private String name;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime deletedAt;
    @Column
    private Long productId;


}
