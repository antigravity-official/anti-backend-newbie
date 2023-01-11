package antigravity.repository;

import antigravity.config.PageParam;
import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbcTemplate2;

    // 예시 메서드입니다.
    public Product findById(Long id) {
        try{
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

        }catch (EmptyResultDataAccessException error){
            return null;
        }

    }

    /**
     * 찜 상품 등록
     * @date 2023.01.09
     * @author 이상우
     * @param productId
     * @param userId
     * @return String
    * */
    public String productLiked(Long userId, Long productId){
        // 결과를 담는 변수
        String result;

        try{
            // 찜 누른 상품이 존재하는 경우
            String selectQuery = "SELECT product_id" +
                    "             FROM favorite" +
                    "             WHERE user_id = :userId" +
                    "             AND product_id = :productId";

            Map<String,Long> selectParms = new HashMap<>();

            selectParms.put("userId", userId);
            selectParms.put("productId", productId);

            Long favoriUserId = jdbcTemplate.queryForObject(selectQuery, selectParms, Long.class);

            result = "400";

        }catch (EmptyResultDataAccessException error){
            // 찜한 상품의 정보가 없을때
            String createQuery = "INSERT INTO favorite(product_id, user_id)" +
                    "             VALUES ("+productId+", "+userId+")";

            Map<String,Long> createParam = new HashMap<>();

            int favoriteResult = jdbcTemplate.update(createQuery, createParam);

            if(favoriteResult <= 0){
                result = "506";
            }

            String updateQuery = "UPDATE product " +
                    "       SET totalLiked=totalLiked+1," +
                    "           viewed = viewed+1 " +
                    "       WHERE id = :productId";

            MapSqlParameterSource updatePram = new MapSqlParameterSource("productId", productId);

            int value = jdbcTemplate.update(updateQuery, updatePram);

            if(value <= 0){
                result= "506";
            }else{
                result = "201";
            }

        }

        return result;
    }


    /**
     * 상품 조회
     * @date 2023.01.12
     * @author 이상우
     * @param pageParam
     * @param id
     * @param liked
     *        liked = false 찜하지 않은 제품 목록
     *        liked = true 찜한 제품 목록
     * @return List<ProductResponse>
     * */
    public List<ProductResponse> favoriteProductList(PageParam pageParam, Long id, boolean liked){
        List<ProductResponse> productList;
        String query;
        try{
            // 찜한 상품만 조회
            if(liked){
                query = "SELECT " +
                        " po.id, po.sku, po.name, po.price, po.quantity, po.totalLiked, po.viewed, po.created_at, po.updated_at " +
                        "FROM product po " +
                        "Inner join favorite fv on (po.id = fv.product_id)" +
                        "WHERE fv.user_id = :id " +
                        "LIMIT :page, :size";
            }else {
                // 찜하지 않은 상품 조회
                query = "SELECT " +
                        " po.id, po.sku, po.name, po.price, po.quantity, po.totalLiked, po.viewed, po.created_at, po.updated_at " +
                        "FROM product po " +
                        "LEFT JOIN favorite fa on (po.id = fa.product_id) " +
                        "WHERE fa.product_id is null " +
                        "LIMIT :page, :size";
            }


            MapSqlParameterSource params = new MapSqlParameterSource("id", id)
                                                .addValue("page", pageParam.getStartPage())
                                                .addValue("size", pageParam.getSize());

            productList = jdbcTemplate.query(query, params, (rs, rowNum) ->
                    ProductResponse.builder()
                            .id(rs.getLong("id"))
                            .sku(rs.getString("sku"))
                            .name(rs.getString("name"))
                            .price(rs.getBigDecimal("price").intValue())
                            .quantity(rs.getInt("quantity"))
                            .likes(liked ? true : false)
                            .totalLiked(rs.getInt("totalliked"))
                            .viewed(rs.getInt("viewed"))
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                            .build());

        }catch (EmptyResultDataAccessException error){
            System.out.println("조회 오류 발생");
            productList = null;
        }
        //like 파라미터가 있는 경우 찜한 상품만 조회

        return productList;
    }

    /**
     * 전체 상품 조회 및 찜 상품 true
     * @date 2023.01.12
     * @author 이상우
     * @param param
     * @param id
     * @return List<ProductResponse>
     * */
    public List<ProductResponse> findAllProductList(PageParam param, Long id){
        List<ProductResponse> productAllList;
        try{

            String query = "SELECT " +
                    "po.id, po.sku, po.name, po.price, po.quantity, po.totalLiked, po.viewed, po.created_at, po.updated_at, " +
                        "(SELECT EXISTS(select fv.user_id " +
                        "FROM favorite fv " +
                        "WHERE fv.user_id = :id " +
                        "AND fv.product_id = po.id)) as liked " +
                    "FROM product po " +
                    "LIMIT :page, :size";

            MapSqlParameterSource params = new MapSqlParameterSource("id", id)
                    .addValue("page", param.getPage())
                    .addValue("size", param.getSize());

            productAllList = jdbcTemplate.query(query,params,(rs,rowNum) ->
                    ProductResponse.builder()
                            .id(rs.getLong("id"))
                            .sku(rs.getString("sku"))
                            .name(rs.getString("name"))
                            .price(rs.getBigDecimal("price").intValue())
                            .quantity(rs.getInt("quantity"))
                            .likes(rs.getBoolean("liked"))
                            .totalLiked(rs.getInt("totalliked"))
                            .viewed(rs.getInt("viewed"))
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                            .build());

        }catch (EmptyResultDataAccessException error){
            System.out.println("조회 오류");
            productAllList = null;
        }

        return productAllList;
    }

}
