package antigravity.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class Wanted {
    private Long id;
    private String sku;
    private String email;

}
