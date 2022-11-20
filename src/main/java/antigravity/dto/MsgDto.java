package antigravity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "API 통신 성공시 반환하는 객체")
public class MsgDto {

    @ApiModelProperty(value = "성공시 해당 응답 메세지",dataType = "string",example = "응답이 성공하셨습니다.")
    private String msg;
    @ApiModelProperty(value = "성공시 해당 응답 코드",dataType = "long",example = "201")
    private long code;
}
