package antigravity.service;

import antigravity.entity.LikedStatus;
import antigravity.entity.Member;
import antigravity.entity.Product;
import antigravity.entity.View;
import antigravity.entity.dto.LikedDto.Create;
import antigravity.entity.dto.exception.BadRequestException;
import antigravity.repository.ViewRepository;
import antigravity.repository.MemberRepository;
import antigravity.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class ViewCreatorTest {

    @ExtendWith(MockitoExtension.class)
    @Nested
    class Mock {


    }


    @DisplayName("찜 등록 테스트")
    @ExtendWith(SpringExtension.class)
    @SpringBootTest
    @Nested
    class Boot {

        @Autowired
        LikedCreator creator;

        @MockBean
        ProductRepository productRepository;

        @MockBean
        MemberRepository memberRepository;

        @MockBean
        ViewRepository viewRepository;

        @Test
        @Transactional
        @DisplayName("성공")
        void testSuccessCreateLiked() {
            Optional<Product> optionalProduct = Optional.of(random(Product.class));
            Optional<Member> optionalMember = Optional.of(random(Member.class));
            Product product = optionalProduct.orElseThrow();
            Member member = optionalMember.orElseThrow();
            View expectedView = new View(1L, product, member, LikedStatus.LIKED, null);

            given(productRepository.findById(any())).willReturn(optionalProduct);
            given(memberRepository.findById(any())).willReturn(optionalMember);
            given(viewRepository.save(any(View.class))).willReturn(expectedView);

            Create.Condition condition = new Create.Condition(product.getId(), member.getId(), LikedStatus.LIKED);
            Create.Response response = creator.create(condition);

            assertNotNull(response);
            assertNotNull(response.getProductId());
            assertNotNull(response.getMemberId());

            assertEquals(condition.getLikedStatus(), response.getLikedStatus());
            assertEquals(product.getId(), response.getProductId());
            assertEquals(member.getId(), response.getMemberId());
        }

        @Test
        @DisplayName("에러 - 상품이 없는 경우")
        void testFailureCreateNotExistProduct() {
            given(productRepository.findById(any())).willReturn(Optional.empty());

            Create.Condition condition = random(Create.Condition.class);

            assertThrows(BadRequestException.class, () -> creator.create(condition));
        }

        @Test
        @DisplayName("에러 - 사용자가 없는 경우")
        void testFailureCreateNotExistMember() {
            given(memberRepository.findById(any())).willReturn(Optional.empty());

            Create.Condition condition = random(Create.Condition.class);

            assertThrows(BadRequestException.class, () -> creator.create(condition));
        }
    }


}
