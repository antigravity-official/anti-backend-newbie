package antigravity.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
	private final JPAQueryFactory jpaQueryFactory;

}
