package antigravity.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

	@CreatedDate
	@Column(updatable = false, name = "created_at")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedTime;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
}
