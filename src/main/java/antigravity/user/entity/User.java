package antigravity.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class User {
    private Long id;
    @Email
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
