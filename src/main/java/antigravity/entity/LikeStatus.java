package antigravity.entity;

import lombok.Getter;

@Getter
public enum LikeStatus {

    NONE("NONE", 0),
    LIKE("LIKE", 1);

    private final String desc;
    private final Integer stateCode;

    LikeStatus(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}