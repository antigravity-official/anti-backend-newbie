package antigravity.service;


import antigravity.payload.PostResponse;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;


    public ResponseEntity<PostResponse> like(long userId, long productId) {

        PostResponse postResponse = new PostResponse();
        try {
            productRepository.createLike(userId, productId);
            productRepository.increaseHits(productId);
            postResponse.setStatusCode(201);
            postResponse.setResult(PostResponse.SUCCESS);
            return new ResponseEntity<PostResponse>(postResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            postResponse.setStatusCode(400);
            postResponse.setResult(PostResponse.FAIL);
            return new ResponseEntity<PostResponse>(postResponse, HttpStatus.BAD_REQUEST);
        }

    }

    public List<ProductResponse> find(Long userId, String liked, String page, String size) {
        if (liked == null || liked.trim().isEmpty()) {
            return productRepository.findAll(userId, page, size);
        }

        if (Boolean.parseBoolean(liked)) {
            return productRepository.findByLiked(userId);
        }
        return productRepository.findByNotLiked(userId);
    }
}

