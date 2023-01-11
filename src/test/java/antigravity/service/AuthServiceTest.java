package antigravity.service;

import antigravity.DataBaseCleanUp;
import antigravity.common.BaseException;
import antigravity.entity.Member;
import antigravity.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthServiceTest {
    @Autowired
    AuthService authService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DataBaseCleanUp dataBaseCleanUp;

    @BeforeEach
    public void setUp() {
        dataBaseCleanUp.cleanUp();
        Member member = Member.builder().id(1L).email("test").build();
        memberRepository.save(member);
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