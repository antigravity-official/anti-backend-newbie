package antigravity.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import antigravity.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor     //생성자 자동으로 만들어주는 어노테이션
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    //데이터를 저장하기 위한 곳
    // 예시 메서드입니다.
    public Product findById(Long id) {
        String query = "SELECT id, sku, name, price, quantity, created_at" +
                "       FROM product WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                Product.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .quantity(rs.getInt("quantity"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build());
    }
    
    
    @Override
    public List<Product> selectProduct() {
    	log.debug("selectProduct");
    	List<Product> result = jdbcTemplate.query("SELECT id, sku,name,price FROM product", productRowMapper());
    	log.info("조회 결과 {}",result);
    	
    	return result;
    }
    
    
    private RowMapper<Product> productRowMapper(){
    	return new RowMapper<Product>() {
    			@Override
    			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
    				
    				Product product = new Product();
    				product.setId(rs.getLong("id"));
    				product.setSku(rs.getString("sku"));
    				product.setName(rs.getString("name"));
    				product.setPrice(rs.getBigDecimal("price"));
    				
    				return product;
    			}
    	
    	};
    	
    }

}
