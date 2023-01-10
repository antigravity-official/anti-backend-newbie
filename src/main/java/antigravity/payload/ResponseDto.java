package antigravity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
    private String msg; // 성공시 응답 메세지
    private long code;  // 성공시 응답 코드

}
