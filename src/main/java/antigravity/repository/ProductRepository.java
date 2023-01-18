package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.Member;
import antigravity.entity.Wanted;
import antigravity.payload.ProductResponse;
import antigravity.payload.ProductResponseRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbcTemplate2;

    // 예시 메서드입니다.
    public Product findById(Long id) {
        String query = "SELECT id, sku, name, price, quantity,viewed, created_at" +
                "       FROM product WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                Product.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .quantity(rs.getInt("quantity"))
                        .viewed(rs.getInt("viewed"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build());
    }

    public void viewedUpdate(ProductResponse productResponse){
        String query = "UPDATE product SET viewed = :viewed WHERE sku = :sku";

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("viewed", productResponse.getViewed());
        params.put("sku", productResponse.getSku());

        jdbcTemplate.update(query, params);
    }



    public void ImportWanted(String sku, String email){

        String query = "INSERT INTO Wanted(email, sku)" +
                "VALUES (:email,:sku)";
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("sku", sku);

        jdbcTemplate.update(query, params);
    }

    public Wanted findWaById(Long id) {
        String query = "SELECT id, sku, email" +
                "   FROM Wanted WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                Wanted.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .email(rs.getString("email"))
                        .build());
    }
    public Wanted findWaByEmail(String email) {
        String query = "SELECT sku, name" +
                "   FROM Wanted WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);

        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                Wanted.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .email(rs.getString("email"))
                        .build());
    }

    public Member findUserByEmail(String email) {
        String query = "SELECT *" +
                "   FROM Member WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);

        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                Member.builder()
                        .id(rs.getLong("id"))
                        .email(rs.getString("email"))
                        .name(rs.getString("name"))
                        .build());
    }

    public ArrayList<ProductResponse> Wanted_List(String email, Boolean Liked){
        ArrayList<ProductResponse> result = null;
        String query = "SELECT * FROM PRODUCT A, (SELECT * FROM WANTED WHERE email = :email) B" +
                "       WHERE A.sku = B.sku AND A.Liked = :Liked";


        Map<String, Object> params = new HashMap<String, Object>();

        params.put("email", email);
        params.put("Liked", Liked);

        result = (ArrayList<ProductResponse>) jdbcTemplate.query(query, params, new ProductResponseRowMapper());

        return result;
    }
}
