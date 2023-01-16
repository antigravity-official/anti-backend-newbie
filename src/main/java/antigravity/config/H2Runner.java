package antigravity.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
@Component
@Slf4j
public class H2Runner implements ApplicationRunner {

    private final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try (Connection connection = dataSource.getConnection()) {
            log.info("connect = {}", connection);
            String URL = connection.getMetaData().getURL();
            log.info("url = {}", URL);
            String User = connection.getMetaData().getUserName();
            log.info("User = {}", User);
        }
    }

}