package antigravity.repository;

import antigravity.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
     Optional<User> findById(Long id);

}
