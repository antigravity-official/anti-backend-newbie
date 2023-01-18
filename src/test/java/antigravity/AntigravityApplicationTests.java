package antigravity;

import antigravity.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
class AntigravityApplicationTests {

    @Autowired
    MockMvc mvc;




    @Test
    void contextLoads() {
    }

}
