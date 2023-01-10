package antigravity.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long userId);

    List<Member> findAll();

}
