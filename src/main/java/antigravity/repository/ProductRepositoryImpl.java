package antigravity.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import antigravity.dto.LikedDto;
import antigravity.dto.UserDto;
import antigravity.entity.Liked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor     //생성자 자동으로 만들어주는 어노테이션
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    // 예시 메서드입니다.
    

    @Override
    public int selectCnt(LikedDto likedDto, UserDto userDto) {
    	
    	String sql ="SELECT count(*) FROM LIKED"
    			+"WHERE userId = : userId and productId = : productId";
    	
    	SqlParameterSource params = new MapSqlParameterSource("userId",userDto.getId())
    				                .addValue("productId", likedDto.getProductId());
   
    	int result = jdbcTemplate.queryForObject(sql,params,Integer.class);
    	return result;
    }
    
    
    @Override
    public int insertLiked(LikedDto likedDto,UserDto userDto) {
    	log.debug("insertLiked");
    	String sql = "INSERT INTO LIKED (likedNo,productId,userId)"
    			+"VALUES(:likedNo,:productId,:userId)"
    			+ "WHERE userId =: userId";
    	SqlParameterSource params = new MapSqlParameterSource("likedNo",likedDto.getLikedNo())
    	 								.addValue("productId",likedDto.getProductId())
    	 								.addValue("userId",userDto.getId());
    	return jdbcTemplate.update(sql, params);
    }
    
    @Override
    public void updateViews( LikedDto likedDto, UserDto userDto) {
    	log.debug("updateVeiws");
    	
    	String sql ="UPDATE LIKED SET view+1 "
    			+ "WHERE userId = :userId and productId =:productId";
    
    	SqlParameterSource params = new MapSqlParameterSource("procutId",likedDto.getProductId())
    				                         .addValue("userId", userDto.getId());
    	
    	int result = jdbcTemplate.update(sql, params);
    	log.info("update  결과 {}",result);	
    }

    
}
