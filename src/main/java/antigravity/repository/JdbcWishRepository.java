package antigravity.repository;

import antigravity.entity.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcWishRepository implements WishRepository{
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Wish> findById(Long userId, Long productId) {
        String query = "SELECT user_id, product_id, created_at" +
                "       FROM wish " +
                "       WHERE user_id = ? and product_id = ?";

        List<Wish> results = jdbcTemplate.query(query, wishMapper, new Object[]{userId, productId});
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }


    @Override
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
