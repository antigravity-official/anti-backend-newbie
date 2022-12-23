package antigravity.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikedStatus {
    NONE,
    LIKED,
    UNLIKED;

    public boolean liked() {
        return this == LIKED;
    }
}
