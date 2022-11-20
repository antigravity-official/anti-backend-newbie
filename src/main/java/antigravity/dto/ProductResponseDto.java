package antigravity.dto;

import antigravity.domain.entity.Want;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "찜 상품 조회시 들어가는 변수들")
public class ProductResponseDto {

    @ApiModelProperty(value = "상품 아이디",dataType = "Long",example = "1")
    private Long id;
    @ApiModelProperty(value = "상품 고유값",dataType = "String",example = "G2000000019")
    private String sku;
    @ApiModelProperty(value = "상품명",dataType = "String",example = "No1. 더핏세트")
    private String name;
    @ApiModelProperty(value = "가격",dataType = "BigDecimal",example = "42800")
    private BigDecimal price;
    @ApiModelProperty(value = "재고수량",dataType = "int",example = "10")
    private int quantity;

    @ApiModelProperty(value = "필요한 경우 찜한 상품임을 표시 (찜 여부)",dataType = "boolean",example = "false")
    private boolean liked; //
    @ApiModelProperty(value = "상품이 받은 모든 찜 개수",dataType = "int",example = "2")
    private int totalLiked;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Builder.Default
    @ApiModelProperty(value = "찜 등록 여부 확인 위한 변수로 사용만하고 반환안함",dataType = "Want.class",example = "Want객체 내용")
    private List<Want> wantList= new ArrayList<>();
    @ApiModelProperty(value = "상품 조회 수",dataType = "int",example = "1")
    private int view;
    @ApiModelProperty(value = "상품 생성일시",dataType = "LocalDateTime",example = "04:53:58.733")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "상품 수정일시",dataType = "LocalDateTime",example = "04:53:58.733")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @ApiModelProperty(value = "상품 삭제일시",dataType = "LocalDateTime",example = "04:53:58.733")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deletedAt;

    @QueryProjection
    public ProductResponseDto(Long id, String sku, String name, BigDecimal price, int quantity, List<Want> wantList,
                              int totalLiked, int view, LocalDateTime createdAt, LocalDateTime updatedAt,LocalDateTime deletedAt){
        this.id=id;
        this.sku=sku;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
        this.wantList = wantList;
        this.totalLiked=totalLiked;
        this.view=view;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.deletedAt = deletedAt;
    }
}
