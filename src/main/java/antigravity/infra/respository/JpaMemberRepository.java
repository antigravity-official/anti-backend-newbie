package antigravity.infra.respository;

import antigravity.domain.user.Member;
import antigravity.domain.user.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member, Long> {


}
