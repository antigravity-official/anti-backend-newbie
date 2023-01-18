package antigravity.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {

    private Long id;
    private String email;
    private String name;


}
