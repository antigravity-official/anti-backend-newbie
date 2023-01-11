package antigravity;

import antigravity.repository.LikeHistoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class DataBaseCleanUp {
    @Autowired
    LikeHistoryRepository likeHistoryRepository;
    @Autowired
    List<? extends CrudRepository> crudRepositories;

    public void cleanUp() {
        likeHistoryRepository.deleteAll();
        crudRepositories.stream().forEach(CrudRepository::deleteAll);
    }
}
