package antigravity.payload.request;

import lombok.Getter;

@Getter
public class SearchRequest {
    private final Boolean liked;

    public SearchRequest(Boolean liked) {
        this.liked = liked;
    }
}
