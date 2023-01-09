package antigravity.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	public void createdAt() {
		this.createdAt = LocalDateTime.now();
	}
}
