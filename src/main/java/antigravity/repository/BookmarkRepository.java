package antigravity.repository;

import antigravity.entity.Bookmark;
import antigravity.repository.querydsl.BookmarkRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long>, BookmarkRepositoryCustom {

    Optional<Bookmark> findByUserIdAndProductId(Long userId, Long ProductId);
}
