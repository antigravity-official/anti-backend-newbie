package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;

@Builder
@ToString
@Getter
public class User {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
