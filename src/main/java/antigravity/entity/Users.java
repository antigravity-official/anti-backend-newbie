package antigravity.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 다른곳에서 해당 엔티티를 직접생성 못하게
public class Users {

    @Id @GeneratedValue
    @Column(name="users_id")
    private Long id;

    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;


    @OneToMany(mappedBy = "users")
    private List<Wish> wishList = new ArrayList<>();

    @Builder
    public Users(String email, String name){
        this.email=email;
        this.name=name;
    }

}
