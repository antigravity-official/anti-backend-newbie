package antigravity.payload;

import antigravity.dto.ProductDTO;

import java.util.List;

public class PageResponse {
    private List<ProductDTO> productDTOList;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
