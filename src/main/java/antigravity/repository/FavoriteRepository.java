package antigravity.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FavoriteRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

}
