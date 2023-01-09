package antigravity.entity;

import lombok.*;

@Builder
@ToString
@Getter
public class ViewCount {

    private Long productId;
    private Long views;

}
