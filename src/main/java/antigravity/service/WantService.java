package antigravity.service;

import antigravity.domain.entity.Product;
import antigravity.domain.entity.User;
import antigravity.domain.entity.Want;
import antigravity.domain.repository.ProductRepository;
import antigravity.domain.repository.UserRepository;
import antigravity.domain.repository.WantRepository;
import antigravity.dto.MsgDto;
import antigravity.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;


@RequiredArgsConstructor
@Service
public class WantService {
    private final ProductRepository productRepository;
    
    private final UserRepository userRepository;
    
    private final WantRepository wantRepository;

    @Transactional
    @Cacheable(value = "post")
    public ResponseEntity<MsgDto> createWantItems(Long productId, Long userId) {
        Product product= productRepository.findById(productId).orElseThrow(
                ()-> new NoSuchElementException(ErrorMessage.NOT_EXIST_PRODUCT.getMsg()));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException(ErrorMessage.NOT_EXIST_USER.getMsg()));
        Optional<Want> want = wantRepository.findByProductAndUser(product,user);
        if(want.isEmpty())
            wantRepository.save(Want.builder().product(product).user(user).build());
        else
            if(want.get().isWant())
                throw new RejectedExecutionException(ErrorMessage.EXIST_WANT_PRODUCT.getMsg());
            else
                want.get().changeWant();
        return ResponseEntity.status(HttpStatus.CREATED).body(MsgDto.builder().msg("찜 등록에 성공하셨습니다.").code(201).build());
    }

}
