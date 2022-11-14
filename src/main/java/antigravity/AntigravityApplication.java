package antigravity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AntigravityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntigravityApplication.class, args);
	}

}
