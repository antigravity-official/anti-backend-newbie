package antigravity.controller;

import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.payload.CreateWishListRequest;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class WishListControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("찜 상품 등록")
    void wishListPost() throws Exception {

        Optional<User> user = userRepository.findById(2L);
        Optional<Product> product = productRepository.findById(21L);

        CreateWishListRequest createWishListRequest = new CreateWishListRequest();
        createWishListRequest.setUser(user.get());
        createWishListRequest.setProduct(product.get());

        String content = objectMapper.writeValueAsString(createWishListRequest);

        // expected
        mockMvc.perform(post("/{userId}/products/liked/{productId}", user.get().getId(), product.get().getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("post"));
    }

    @Test
    @DisplayName("찜 상품 조회")
    void wishListGet() throws Exception {

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/{userId}/products?liked=true&page=0&size=10", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("get", RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("userId").description("유저 아이디")
                ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("content[].productId").description("상품 아이디"),
                                PayloadDocumentation.fieldWithPath("content[].sku").description("상품 고유값"),
                                PayloadDocumentation.fieldWithPath("content[].name").description("상품명"),
                                PayloadDocumentation.fieldWithPath("content[].price").description("가격"),
                                PayloadDocumentation.fieldWithPath("content[].quantity").description("재고수량"),
                                PayloadDocumentation.fieldWithPath("content[].liked").description("찜 여부"),
                                PayloadDocumentation.fieldWithPath("content[].totalLiked").description("모든 찜 개수"),
                                PayloadDocumentation.fieldWithPath("content[].viewed").description("상품 조회 수"),
                                PayloadDocumentation.fieldWithPath("content[].createdAt").description("상품 생성일시"),
                                PayloadDocumentation.fieldWithPath("content[].updatedAt").description("상품 수정일시"),
                                PayloadDocumentation.fieldWithPath("pageable.sort.empty").ignored(),
                                PayloadDocumentation.fieldWithPath("pageable.sort.sorted").ignored(),
                                PayloadDocumentation.fieldWithPath("pageable.sort.unsorted").ignored(),
                                PayloadDocumentation.fieldWithPath("pageable.offset").ignored(),
                                PayloadDocumentation.fieldWithPath("pageable.pageSize").ignored(),
                                PayloadDocumentation.fieldWithPath("pageable.pageNumber").ignored(),
                                PayloadDocumentation.fieldWithPath("pageable.paged").ignored(),
                                PayloadDocumentation.fieldWithPath("pageable.unpaged").ignored(),
                                PayloadDocumentation.fieldWithPath("last").ignored(),
                                PayloadDocumentation.fieldWithPath("totalElements").ignored(),
                                PayloadDocumentation.fieldWithPath("totalPages").ignored(),
                                PayloadDocumentation.fieldWithPath("size").ignored(),
                                PayloadDocumentation.fieldWithPath("number").ignored(),
                                PayloadDocumentation.fieldWithPath("sort").ignored(),
                                PayloadDocumentation.fieldWithPath("sort.empty").ignored(),
                                PayloadDocumentation.fieldWithPath("sort.sorted").ignored(),
                                PayloadDocumentation.fieldWithPath("sort.unsorted").ignored(),
                                PayloadDocumentation.fieldWithPath("first").ignored(),
                                PayloadDocumentation.fieldWithPath("numberOfElements").ignored(),
                                PayloadDocumentation.fieldWithPath("empty").ignored()
                        )
                ));
    }
}