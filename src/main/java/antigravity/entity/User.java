package antigravity.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<WishList> wishLists = new ArrayList<>();
}
