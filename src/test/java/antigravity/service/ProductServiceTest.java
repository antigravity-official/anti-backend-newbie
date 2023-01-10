package antigravity.service;

import antigravity.entity.Heart;
import antigravity.entity.ViewCount;
import antigravity.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final Long memberId = 2L;
    private final Long productId = 1L;
    @InjectMocks
    private ProductService service;

    @Mock
    ProductRepository repository;

    @DisplayName("찜하지 않은 상품인 경우 heart 테이블에 insert")
    @Test
    public void isNotAlreadyLikedTest() {
        List<Heart> list = new ArrayList<Heart>();
        when(repository.isAlreadyLiked(memberId, productId)).thenReturn(list);
        when(repository.checkViewCount(productId)).thenReturn(null);
        when(repository.addViewCount(productId)).thenReturn(1);

        service.isAlreadyLiked(memberId, productId);
        verify(repository).likeProduct(memberId, productId);
    }

    @DisplayName("이미 찜한 상품인 경우 heart 테이블에서 delete")
    @Test
    public void isAlreadyLikedTest() {
        List<Heart> list = new ArrayList<Heart>();
        list.add(new Heart());
        when(repository.isAlreadyLiked(memberId, productId)).thenReturn(list);

        service.isAlreadyLiked(memberId, productId);
        verify(repository).deleteLikedProduct(memberId, productId);
    }

    @DisplayName("view_count 테이블에 해당 상품 insert")
    @Test
    public void checkViewCountTest1() {
        when(repository.checkViewCount(productId)).thenReturn(null);

        service.checkViewCount(productId);
        verify(repository).addViewCount(productId);
    }

    @DisplayName("view_count 테이블에 해당 상품 조회수 update")
    @Test
    public void checkViewCountTest2() {
        when(repository.checkViewCount(productId)).thenReturn(ViewCount.builder().productId(productId).views(1L).build());

        service.checkViewCount(productId);
        verify(repository).updateViewCount(productId);
    }
}
