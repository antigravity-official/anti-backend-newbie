package antigravity.config;

import lombok.Getter;

@Getter
public class PageParam {
    /** 현재 페이지 번호 */
    private int page;

    /** 페이지당 출력할 데이터 개수 */
    private int size;

    public PageParam(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getStartPage() {
        return (page - 1) * size;
    }


}
