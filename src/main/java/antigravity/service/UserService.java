package antigravity.service;

import antigravity.dto.UserDTO;
import antigravity.entity.User;
import antigravity.exception.CustomBadRequestException;
import antigravity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean isExist(Long id){
        boolean isExist = userRepository.existsById(id);
        return isExist;
    }

}
