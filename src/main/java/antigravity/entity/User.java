package antigravity.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
