package antigravity.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 120, nullable = false)
	private String email;

	@Column(length = 45)
	private String name;

	private LocalDateTime deletedAt;
}
