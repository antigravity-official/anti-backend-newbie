package antigravity.product.presentation;

import javax.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.ObjectUtils;

@Getter
public class SearchProductRequest {
    private Boolean liked;

    @Positive(message = "페이지는 1부터 입력할 수 있습니다.")
    private Integer page;

    @Range(min = 1, max = 50, message = "size는 1부터 50까지 입력 가능합니다.")
    private Integer size;

    public SearchProductRequest(Boolean liked, Integer page, Integer size) {
        setLiked(liked);
        setPage(page);
        setSize(size);
    }

    private void setLiked(Boolean liked) {
        this.liked = liked;
    }

    private void setPage(Integer page) {
        if (ObjectUtils.isEmpty(page)) {
            page = 1;
        }
        this.page = page;
    }

    private void setSize(Integer size) {
        if (ObjectUtils.isEmpty(size)) {
            size = 10;
        }
        this.size = size;
    }
}
