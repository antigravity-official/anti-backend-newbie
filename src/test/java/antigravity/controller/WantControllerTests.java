package antigravity.controller;




import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {WantController.class})
@MockBean(JpaMetamodelMappingContext.class)
public class WantControllerTests {

    @Autowired
    private WantController wantController;
    private MockMvc mockMvc;

    @Before
    public void createController() {
        mockMvc = MockMvcBuilders.standaloneSetup(wantController).build();
    }

//    @Autowired
//    private WantService wantService;
    private static Long PRODUCT_TEST_ID=1L;

    @DisplayName("헤더에 유저 정보가 없는 경우")
    @Test
    void userInterceptorFailTest() throws Exception {
        RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/products/liked/{productId}", PRODUCT_TEST_ID).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(reqBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"statusCode\":\"400 BAD_REQUEST\",\"data\":\"Can't find a header info\"}"));
//        mockMvc.perform(post("/products/liked/{productId}", PRODUCT_TEST_ID))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string("{\"statusCode\":\"400 BAD_REQUEST\",\"data\":\"Can't find a header info\"}"));
    }
}
