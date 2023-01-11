package antigravity.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public boolean findId(Long id){

        String query = "SELECT EXISTS(" +
                "SELECT *" +
                "FROM " +
                "\"USER\"" +
                "WHERE id = :id " +
                "AND deleted_at is Null" +
                ") as ischk";

        MapSqlParameterSource params = new MapSqlParameterSource("id",id);
        boolean result = jdbcTemplate.queryForObject(query,params,Boolean.class);
        return result;
    }



}
