package antigravity.dto;

import java.time.LocalDateTime;

import antigravity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long id;
	private String email;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime deletedAt;
	
	public User toEntitiy() {
		return User.builder()
				.id(id)
				.email(email)
				.name(name)
				.createdAt(createdAt)
				.deletedAt(deletedAt)
				.build();
		
	}
	

}
