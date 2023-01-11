package antigravity.entity;

import lombok.Builder;

@Builder
public class GetLikedProductByUserParam {
    private Integer userId;
    private Boolean liked;
    private Integer page;
    private Integer size;
}
