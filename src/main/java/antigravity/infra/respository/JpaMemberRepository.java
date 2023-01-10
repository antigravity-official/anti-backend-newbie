package antigravity.infra.respository;

import antigravity.domain.member.Member;
import antigravity.domain.member.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member, Long> {


}
