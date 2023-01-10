package antigravity.service;

import antigravity.common.BaseException;
import antigravity.entity.Member;
import antigravity.repository.MemberRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.CrudRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthServiceTest {

    AuthService authService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    List<? extends CrudRepository> crudRepositories;

    @BeforeEach
    public void setUp() {
        crudRepositories.stream().forEach(CrudRepository::deleteAll);
        Member member = Member.builder().id(1L).email("test").build();
        memberRepository.save(member);
        authService = new AuthService(memberRepository);
    }

    @Test
    public void success() {
        String expectParameter = "1";
        Long result = 1L;
        Assertions.assertThat(authService.validateId(expectParameter)).isEqualTo(result);
    }

    @Test
    public void fail() {
        String expectParameter = "100000";
        Assertions.assertThatThrownBy(() -> authService.validateId(expectParameter)).isInstanceOf(BaseException.class);
    }


}