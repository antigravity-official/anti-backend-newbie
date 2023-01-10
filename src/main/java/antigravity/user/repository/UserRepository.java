package antigravity.user.repository;


import antigravity.product.domain.entity.DipProduct;
import antigravity.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM user WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, params, userRowMapper()));
    }
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = User.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .build();
            return user;
        };
    }
}
