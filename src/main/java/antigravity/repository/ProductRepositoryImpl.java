package antigravity.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import antigravity.dto.LikedDto;
import antigravity.entity.Liked;
import antigravity.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor     //생성자 자동으로 만들어주는 어노테이션
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    //데이터를 저장하기 위한 곳
//    // 예시 메서드입니다.
//    PUBLIC PRODUCT FINDBYID(LONG ID) {
//        STRING QUERY = "SELECT ID, SKU, NAME, PRICE, QUANTITY, CREATED_AT" +
//                "       FROM PRODUCT WHERE ID = :ID";
//        MAPSQLPARAMETERSOURCE PARAMS = NEW MAPSQLPARAMETERSOURCE("ID", ID);
//
//        RETURN JDBCTEMPLATE.QUERYFOROBJECT(QUERY, PARAMS, (RS, ROWNUM) ->
//                PRODUCT.BUILDER()
//                        .ID(RS.GETLONG("ID"))
//                        .SKU(RS.GETSTRING("SKU"))
//                        .NAME(RS.GETSTRING("NAME"))
//                        .PRICE(RS.GETBIGDECIMAL("PRICE"))
//                        .QUANTITY(RS.GETINT("QUANTITY"))
//                        .CREATEDAT(RS.GETTIMESTAMP("CREATED_AT").TOLOCALDATETIME())
//                        .BUILD());
//    }
//    
    @Override
    public int insertLiked(@Valid LikedDto likedDto) {
    	log.debug("insertLiked");
    	String sql = "INSERT INTO LIKED (likedNo,productId,userId)"
    			+"VALUES(:likedNo,:productId,:userId)";
    	SqlParameterSource params = new MapSqlParameterSource("likedNo",likedDto.getLikedNo())
    	 								.addValue("productId",likedDto.getProductId())
    	 								.addValue("userId",likedDto.getUserId());
    	
    	return jdbcTemplate.update(sql, params);
    }
    

}
