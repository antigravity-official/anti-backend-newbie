package antigravity.payload;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PagingDto {

    @Min(value = 0)
    private int page;
    @Min(value = 0)
    private int size;

    public PagingDto() {
    }

    public PagingDto(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page * 10;
    }

    public int getSize() {
        return (this.page + 1) * 10;
    }
}
