package antigravity.domain.repository;

import antigravity.dto.ProductResponseDto;
import antigravity.mapper.ProductMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static antigravity.domain.entity.QProduct.product;
import static antigravity.domain.entity.QWant.want;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<ProductResponseDto> getWantProducts(Boolean liked, Long userId, Pageable pageable){
        List<ProductResponseDto> content = getProductDtolist(pageable);
        List<ProductResponseDto> exceptContent = new ArrayList<>();
        if(liked == null){
            content.forEach(p->checkWant(p,userId));
        }else {
            content.forEach(p->collectExcept(p,userId,liked,exceptContent));
            content = exceptContent;
        }
        Long count = getCount();

        return new PageImpl<>(content,pageable,count);
    }
    private List<ProductResponseDto> getProductDtolist(Pageable pageable) {
        return queryFactory
                .selectFrom(product)
                .leftJoin(product.wantList, want)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().map(ProductMapper.INSTANCE::orderToDto).collect(Collectors.toList());
    }
    private void collectExcept(ProductResponseDto productResponseDto, Long userId, Boolean liked, List<ProductResponseDto> exceptContent) {
        productResponseDto.setLiked(liked);
        if(liked){
            if(productResponseDto.getWantList().stream().anyMatch(p-> p.getUser().getId().equals(userId) && p.isWant()))
                exceptContent.add(productResponseDto);
        }else{
            if(productResponseDto.getWantList().stream().noneMatch(p->p.getUser().getId().equals(userId) && p.isWant()))
                exceptContent.add(productResponseDto);
        }
        productResponseDto.getWantList().clear();
    }

    private void checkWant(ProductResponseDto productResponseDto, Long userId) {
        if(productResponseDto.getWantList().stream().anyMatch(p->p.getUser().getId().equals(userId)))
            productResponseDto.setLiked(true);
        productResponseDto.getWantList().clear();
    }

    private Long getCount() {
        return queryFactory
                .select(product.count())
                .from(product)
                .fetchOne();
    }
}
