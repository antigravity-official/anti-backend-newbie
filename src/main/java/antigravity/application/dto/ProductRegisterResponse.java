package antigravity.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductRegisterResponse {

    private final Long id;
    private final String sku;
    private final String name;
}
