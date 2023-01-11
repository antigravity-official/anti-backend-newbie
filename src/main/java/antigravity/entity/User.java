package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class User {
    private long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
