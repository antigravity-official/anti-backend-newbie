package antigravity.common.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class CreatedAtAuditingEntity {

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
}
