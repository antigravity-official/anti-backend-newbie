package antigravity.repository;

import antigravity.entity.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class WishRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public Optional<Wish> findById(Long userId, Long productId) {
        String query = "SELECT user_id, product_id, created_at" +
                "       FROM wish " +
                "       WHERE user_id = ? and product_id = ?";

        List<Wish> results = jdbcTemplate.query(query, wishMapper, new Object[]{userId, productId});
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    public void save(Long userId, Long productId) {
        String query = "INSERT INTO wish(user_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(query, userId, productId);
    }

    static RowMapper<Wish> wishMapper = (rs, rowNum) ->
            Wish.builder()
                    .userId(rs.getLong("user_id"))
                    .productId(rs.getLong("product_id"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .build();
}
